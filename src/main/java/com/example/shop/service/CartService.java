package com.example.shop.service;

import com.example.shop.dto.CartDTO;
import com.example.shop.entity.BookEntity;
import com.example.shop.entity.CartEntity;

import java.util.List;

public interface CartService {
    List<CartEntity> getAll();
    CartEntity createCart (CartDTO cartEntity, String email) throws Exception;
    CartEntity findById(Integer id) throws Exception;
    CartEntity update(CartDTO cartEntity, String email) throws Exception;
    String deleteById(Integer id, String email);
}
