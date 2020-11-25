package com.example.shop.repository;

import com.example.shop.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findAllByNameContainingIgnoringCase(String name);
    UserEntity findByEmail(String email);
    UserEntity findByActivationCode(String code);
    List<UserEntity> findAllByDeleted(boolean isDeleted);
    UserEntity findByEmailAndDeleted(String email, Boolean isDeleted);
}
