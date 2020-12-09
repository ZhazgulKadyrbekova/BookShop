package com.example.shop.service;

import com.example.shop.dto.AddressDTO;
import com.example.shop.entity.AddressEntity;

import java.util.List;

public interface AddressService {
    List<AddressEntity> getAll();
    AddressEntity createAddress(AddressDTO address, String email);
    AddressEntity findById(Integer id);
    AddressEntity update(Integer id, AddressDTO address, String email);
    String deleteById(Integer id, String email);
}
