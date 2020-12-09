package com.example.shop.service;

import com.example.shop.dto.CategoryDTO;
import com.example.shop.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> getAll();
    CategoryEntity createCategory (CategoryDTO categoryEntity, String email);
    CategoryEntity findById(Integer id) throws Exception;
    CategoryEntity update(Integer id, CategoryDTO categoryEntity, String email) throws Exception;
    String deleteById(Integer id, String email);
}
