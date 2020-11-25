package com.example.shop.service;

import com.example.shop.dto.UserDTO;
import com.example.shop.dto.UserRegisterDTO;
import com.example.shop.entity.UserEntity;

import java.util.List;

public interface UserService {
    boolean save(UserRegisterDTO userRegisterDTO);
    boolean createUser(UserDTO userDTO);
    void createAdmin(UserEntity user);
    String activateUser(String code);
    boolean sendForgottenPassword(String email);
    boolean changePassword(String email, String password);

    List<UserEntity> getAll();
    List<UserEntity> getAllByName(String name);
    UserEntity getByEmail(String email);
    String blockUser(Integer id, String email);
}
