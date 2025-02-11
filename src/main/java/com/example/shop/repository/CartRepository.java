package com.example.shop.repository;

import com.example.shop.entity.CartEntity;
import com.example.shop.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    List<CartEntity> findAllByDeleted(boolean isDeleted);
    CartEntity findByUser(UserEntity user);
}
