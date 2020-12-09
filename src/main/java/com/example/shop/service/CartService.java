package com.example.shop.service;

import com.example.shop.dto.CartDTO;
import com.example.shop.entity.BookEntity;
import com.example.shop.entity.CartEntity;

import java.util.List;

public interface CartService {
    CartEntity findByEmail(String email) throws Exception;
    CartEntity update(CartDTO cartDTO, String email) throws Exception;
}
