package com.example.shop.service.impl;

import com.example.shop.dto.UserDTO;
import com.example.shop.dto.UserRegisterDTO;
import com.example.shop.entity.HistoryEntity;
import com.example.shop.entity.RoleEntity;
import com.example.shop.entity.UserEntity;
import com.example.shop.exception.UserNotFoundException;
import com.example.shop.repository.HistoryRepository;
import com.example.shop.repository.RoleRepository;
import com.example.shop.repository.UserRepository;
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

    @Override
    public boolean save(UserRegisterDTO userRegisterDTO) {
        UserEntity user = userRepository.findByEmail(userRegisterDTO.getEmail());
        if (user != null) {
            user.setPassword(encoder.encode(userRegisterDTO.getPassword()));
            user.setName(userRegisterDTO.getName());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean createUser(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setEmail(userDTO.getEmail());
        user.setDeleted(true);
        user.setActivationCode(UUID.randomUUID().toString());

        RoleEntity role = roleRepository.save(new RoleEntity("ROLE_USER"));
        user.setRole(role);

        String message = "Link to activate your account: '/register/activate/" +
                user.getActivationCode();
        if (mailService.send(user.getEmail(), "activation code", message)) {
            userRepository.save(user);
//
//            UserEntity userEntity = userRepository.findByEmail(email);
//            HistoryEntity history = new
//                    HistoryEntity("USER", "CREATE id:" + user.getID().toString(), userEntity);
//            historyRepository.save(history);

            return true;
        }
        return false;
    }

    @Override
    public void createAdmin(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public String activateUser(String code) {
        UserEntity user = userRepository.findByActivationCode(code);
        if (user == null) {
            return "error";
        }
        user.setActivationCode(null);
        user.setDeleted(false);
        userRepository.save(user);
        return user.getEmail();
    }

    @Override
    public boolean sendForgottenPassword(String email) {
        UserEntity user = userRepository.findByEmail(email);
        String message = "Link to reset forgotten password: '/register/changePassword";
        return user != null && mailService.send(user.getEmail(), "reset password", message);
    }

    @Override
    public boolean changePassword(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            user.setPassword(encoder.encode(password));
            userRepository.save(user);

            HistoryEntity history = new
                    HistoryEntity("USER", "CHANGE PASSWORD " + user.getEmail(), user);
            historyRepository.save(history);

            return true;
        }
        return false;
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
        user.setDeleted(true);
        userRepository.save(user);

        UserEntity userEntity = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("USER", "BLOCK " + user.getName(), userEntity);
        historyRepository.save(history);

        return "User id " + id + " has been deleted";
    }
}
