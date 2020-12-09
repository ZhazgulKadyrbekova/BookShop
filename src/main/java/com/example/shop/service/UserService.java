package com.example.shop.service;

import com.example.shop.dto.UserAdminDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.entity.UserEntity;

import java.util.List;

public interface UserService {
    String createUser(UserDTO userDTO);
    String createAdmin(UserAdminDTO user);
    String saveAdmin(UserDTO userDTO);
    String activateUser(String code);
    String sendForgottenPassword(String email);
    String changePassword(String email, String password);

    List<UserEntity> getAll();
    List<UserEntity> getAllByName(String name);
    UserEntity getByEmail(String email);
    String blockUser(Integer id, String email);
    String unblockUser(Integer id, String email);

}
