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

    @GetMapping("/getAll")
    public ResponseEntity<List<CartEntity>> getAll() {
        return new ResponseEntity<>(cartService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CartEntity> addCart(@RequestBody CartDTO cartEntity, Principal principal) throws Exception {
        return new ResponseEntity<>(cartService.createCart(cartEntity, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartEntity> getCart(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(cartService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update}")
    public ResponseEntity<CartEntity> updateCart(@RequestBody CartDTO cartEntity, Principal principal) throws Exception{
        return new ResponseEntity<>(cartService.update(cartEntity, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Integer id, Principal principal) {
        return new ResponseEntity<>(cartService.deleteById(id, principal.getName()), HttpStatus.OK);
    }
}
