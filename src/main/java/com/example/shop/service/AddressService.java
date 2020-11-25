package com.example.shop.service;

import com.example.shop.entity.AddressEntity;

import java.util.List;

public interface AddressService {
    List<AddressEntity> getAll();
    AddressEntity createAddress(AddressEntity address, String email);
    AddressEntity findById(Integer id) throws Exception;
    AddressEntity update(AddressEntity address, String email) throws Exception;
    String deleteById(Integer id, String email);
}
