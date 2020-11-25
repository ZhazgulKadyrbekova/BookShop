package com.example.shop.boot;

import com.example.shop.entity.RoleEntity;
import com.example.shop.entity.UserEntity;
import com.example.shop.repository.RoleRepository;
import com.example.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Init implements CommandLineRunner {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
//        RoleEntity role = new RoleEntity("ROLE_ADMIN");
//        roleRepository.save(role);
//        List<RoleEntity> roles = new ArrayList<>();
//        roles.add(role);
//
//        userService.createAdmin(new UserEntity(false, "***REMOVED***@gmail.com", "Zhazgul", "12345678", null, null, true, roles));
    }
}
