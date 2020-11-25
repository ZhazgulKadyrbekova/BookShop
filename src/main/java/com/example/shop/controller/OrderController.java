package com.example.shop.controller;

import com.example.shop.dto.OrderDTO;
import com.example.shop.entity.BookEntity;
import com.example.shop.entity.OrderEntity;
import com.example.shop.repository.OrderRepository;
import com.example.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

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
    public ResponseEntity<OrderEntity> addOrder(@RequestBody OrderDTO orderEntity, Principal principal) throws Exception{
        return new ResponseEntity<>(orderService.createOrder(orderEntity, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> getOrder(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<OrderEntity> updateOrder(@RequestBody OrderDTO orderEntity, Principal principal) throws Exception{
        return new ResponseEntity<>(orderService.update(orderEntity, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer id, Principal principal) {
        return new ResponseEntity<>(orderService.deleteById(id, principal.getName()), HttpStatus.OK);
    }
}
