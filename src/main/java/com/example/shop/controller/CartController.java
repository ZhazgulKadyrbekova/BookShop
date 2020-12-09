package com.example.shop.controller;

import com.example.shop.dto.CartDTO;
import com.example.shop.entity.BookEntity;
import com.example.shop.entity.CartEntity;
import com.example.shop.repository.CartRepository;
import com.example.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<CartEntity> getCart(Principal principal) throws Exception {
        return new ResponseEntity<>(cartService.findByEmail(principal.getName()), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CartEntity> addBooks(@RequestBody CartDTO cartDTO, Principal principal) throws Exception{
        return new ResponseEntity<>(cartService.update(cartDTO, principal.getName()), HttpStatus.OK);
    }

}
