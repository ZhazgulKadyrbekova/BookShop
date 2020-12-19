package com.example.shop.controller;

import com.example.shop.dto.OrderDTO;
import com.example.shop.entity.OrderEntity;
import com.example.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderEntity>> getAll() {
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<OrderEntity> addOrder(@RequestBody OrderDTO orderEntity, Principal principal) {
        return new ResponseEntity<>(orderService.createOrder(orderEntity, principal.getName()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderEntity>> getByEmail(Principal principal) {
        return new ResponseEntity<>(orderService.findAllByEmail(principal.getName()), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderEntity> updateOrder(@PathVariable Integer id, @RequestBody OrderDTO orderEntity, Principal principal) {
        return new ResponseEntity<>(orderService.addBooks(id, orderEntity, principal.getName()), HttpStatus.OK);
    }

    @PutMapping("/updateAddress/{id}")
    public ResponseEntity<OrderEntity> updateAddress(@PathVariable Integer id, @RequestBody OrderDTO orderEntity, Principal principal) {
        return new ResponseEntity<>(orderService.updateAddress(id, orderEntity, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer id, Principal principal) {
        return new ResponseEntity<>(orderService.deleteById(id, principal.getName()), HttpStatus.OK);
    }
}
