package com.example.shop.service.impl;

import com.example.shop.dto.UserAdminDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.entity.CartEntity;
import com.example.shop.entity.HistoryEntity;
import com.example.shop.entity.RoleEntity;
import com.example.shop.entity.UserEntity;
import com.example.shop.exception.UserNotFoundException;
import com.example.shop.repository.CartRepository;
import com.example.shop.repository.HistoryRepository;
import com.example.shop.repository.RoleRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.CartService;
import com.example.shop.service.MailService;
import com.example.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public String createUser(UserDTO userDTO) {
        UserEntity user = new UserEntity();

        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        if (!userDTO.getPassword().equals(userDTO.getPassword2())) {
            throw new RuntimeException("Passwords are not same");
        }
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setDeleted(true);
        user.setActivationCode(UUID.randomUUID().toString());

        RoleEntity role = roleRepository.findByNameContaining("ROLE_USER");
        if (role == null)
            role = roleRepository.save(new RoleEntity("ROLE_USER"));
        user.setRole(role);

        String message = "Link to activate your account: '/register/activate/" +
                user.getActivationCode();
        if (mailService.send(user.getEmail(), "activation code", message)) {
            userRepository.save(user);

            return "Activation code has been send to your email";
        }
        return "Something went wrong";
    }

    @Override
    public String createAdmin(UserAdminDTO userAdminDTO) {
        UserEntity user = new UserEntity();

        user.setEmail(userAdminDTO.getEmail());

        user.setDeleted(true);
        user.setActivationCode(UUID.randomUUID().toString());

        String message = "Link to activate your account: '/register/activate/" +
                user.getActivationCode();
        if (mailService.send(user.getEmail(), "activation code", message)) {
            userRepository.save(user);

            return "Activation code has been send to your email";
        }
        return "Something went wrong";
    }

    @Override
    public String saveAdmin(UserDTO userDTO) {
        UserEntity user = userRepository.findByEmail(userDTO.getEmail());
        if (user == null)
            throw new UserNotFoundException("User " + userDTO.getEmail() + " not found");
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        if (!userDTO.getPassword().equals(userDTO.getPassword2()))
            throw new RuntimeException("Passwords are not same");
        user.setPassword(encoder.encode(userDTO.getPassword()));

        RoleEntity roleEntity = roleRepository.findByNameContaining("ROLE_ADMIN");
        if (roleEntity == null)
            roleEntity = roleRepository.save(new RoleEntity("ROLE_ADMIN"));
        user.setRole(roleEntity);
        userRepository.save(user);
        return "Account info has been saved";
    }

    @Override
    public String activateUser(String code) {
        UserEntity user = userRepository.findByActivationCode(code);
        if (user == null) {
            return "Invalid activation code";
        }
        user.setActivationCode(null);
        user.setDeleted(false);

        CartEntity cart = new CartEntity();
        cart.setUser(user);
        cartRepository.save(cart);

        userRepository.save(user);

        return "Your account has been activated";
    }

    @Override
    public String sendForgottenPassword(String email) {
        UserEntity user = userRepository.findByEmail(email);
        String message = "Link to reset forgotten password: '/register/changePassword";
        if (user != null && mailService.send(user.getEmail(), "reset password", message))
            return "Recovery code sent to your email";
        else return "Something went wrong";
    }

    @Override
    public String changePassword(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            user.setPassword(encoder.encode(password));
            userRepository.save(user);

            HistoryEntity history = new
                    HistoryEntity("USER", "CHANGE PASSWORD " + user.getEmail(), user);
            historyRepository.save(history);

            return "You have successfully changed your password";
        }
        return "Something went wrong";
    }

    @Override
    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAllByDeleted(false);
    }

    @Override
    public List<UserEntity> getAllByName(String name) {
        return userRepository.findAllByNameContainingIgnoringCase(name);
    }

    @Override
    public String blockUser(Integer id, String email) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User id " + id + " not found!"));
        if (user.isDeleted()) {
            throw new UserNotFoundException("User id " + id + " not found!");
        }

        CartEntity cart = cartRepository.findByUser(user);
        cart.setDeleted(true);
        cartRepository.save(cart);

        user.setDeleted(true);
        userRepository.save(user);

        UserEntity userEntity = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("USER", "BLOCK " + user.getEmail(), userEntity);
        historyRepository.save(history);

        return "User id " + id + " has been blocked";
    }

    @Override
    public String unblockUser(Integer id, String email) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User id " + id + " not found"));
        if (!user.isDeleted())
            return "User id " + id + " is not blocked";

        CartEntity cartEntity = cartRepository.findByUser(user);
        cartEntity.setDeleted(false);
        cartRepository.save(cartEntity);

        user.setDeleted(false);
        userRepository.save(user);

        UserEntity userEntity = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("USER", "UNBLOCK" + user.getEmail(), userEntity);
        historyRepository.save(history);

        return "User id " + id + " has been unblocked";
    }
}
