package com.example.shop.service;

import com.example.shop.dto.OrderDTO;
import com.example.shop.entity.OrderEntity;

import java.util.List;

public interface OrderService {
    List<OrderEntity> getAll();
    OrderEntity createOrder (OrderDTO orderEntity, String email) throws Exception;
    OrderEntity findById(Integer id) throws Exception;
    OrderEntity update(OrderDTO orderEntity, String email) throws Exception;
    String deleteById(Integer id, String email);
}
