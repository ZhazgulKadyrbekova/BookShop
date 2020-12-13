package com.example.shop.boot;

import com.example.shop.entity.RoleEntity;
import com.example.shop.entity.UserEntity;
import com.example.shop.repository.RoleRepository;
import com.example.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
//        RoleEntity role = roleRepository.findByNameContaining("ROLE_SUPER_ADMIN");
//        if (role == null)
//            role = roleRepository.save(new RoleEntity("ROLE_SUPER_ADMIN"));
//
//        userService.createSuperAdmin(new
//                UserEntity("zhazgul.j@gmail.com", "AAA", "123456", null, role, null));
    }
}
