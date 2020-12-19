package com.example.shop.controller;

import com.example.shop.entity.HistoryEntity;
import com.example.shop.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    HistoryService service;

    @GetMapping("/getAll")
    public ResponseEntity<List<HistoryEntity>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoryEntity> getHistory(@PathVariable("id") Integer id) throws Exception {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHistory(@PathVariable("id") Integer id, Principal principal) {
        return new ResponseEntity<>(service.deleteById(id, principal.getName()), HttpStatus.OK);
    }

}
