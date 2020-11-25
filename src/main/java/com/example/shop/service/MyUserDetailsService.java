package com.example.shop.service;

import com.example.shop.entity.RoleEntity;
import com.example.shop.entity.UserEntity;
import com.example.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService  implements UserDetailsService {
    @Autowired
    private UserRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userAccount = userAccountRepository.findByEmailAndDeleted(email, false);
        List<RoleEntity> roles = new ArrayList<>();
        roles.add(userAccount.getRole());
        return new User(userAccount.getEmail(), userAccount.getPassword(), roles);
    }
}
