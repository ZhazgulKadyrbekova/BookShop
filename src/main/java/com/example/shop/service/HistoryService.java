package com.example.shop.service;

import com.example.shop.entity.HistoryEntity;

import java.util.List;

public interface HistoryService {
    List<HistoryEntity> getAll();
    HistoryEntity findById(Integer id) throws Exception;
    String deleteById(Integer id, String email);
}
