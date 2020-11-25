package com.example.shop.controller;

import com.example.shop.entity.AddressEntity;
import com.example.shop.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    AddressService addressService;

    @GetMapping("/getAll")
    public ResponseEntity<List<AddressEntity>> getAll() {
        return new ResponseEntity<>(addressService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressEntity> getById(@PathVariable("id") Integer id) throws Exception {
        return new ResponseEntity<>(addressService.findById(id),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AddressEntity> addAddress(@RequestBody AddressEntity address, Principal principal) {
        return new ResponseEntity<>(addressService.createAddress(address, principal.getName()), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<AddressEntity> updateAddress(@RequestBody AddressEntity address, Principal principal) throws Exception {
        return new ResponseEntity<>(addressService.update(address, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable("id") Integer id, Principal principal) {
        return new ResponseEntity<>(addressService.deleteById(id, principal.getName()), HttpStatus.OK);
    }
}
