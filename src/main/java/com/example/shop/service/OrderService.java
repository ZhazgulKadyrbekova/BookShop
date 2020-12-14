package com.example.shop.service;

import com.example.shop.dto.OrderDTO;
import com.example.shop.entity.OrderEntity;

import java.util.List;

public interface OrderService {
    List<OrderEntity> getAll();
    List<OrderEntity> findAllByEmail(String email);
    OrderEntity createOrder (OrderDTO orderEntity, String email);
    OrderEntity addBooks(Integer id, OrderDTO orderEntity, String email);
    OrderEntity updateAddress(Integer id, OrderDTO orderEntity, String email);
    String deleteById(Integer id, String email);
}
