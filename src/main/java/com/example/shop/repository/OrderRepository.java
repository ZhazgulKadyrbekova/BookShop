package com.example.shop.repository;

import com.example.shop.entity.OrderEntity;
import com.example.shop.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    List<OrderEntity> findAllByDeleted(boolean isDeleted);
    List<OrderEntity> findAllByUser(UserEntity user);
}
