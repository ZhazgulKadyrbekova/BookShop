package com.example.shop.service;

import com.example.shop.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> getAll();
    CategoryEntity createCategory (CategoryEntity categoryEntity, String email);
    CategoryEntity findById(Integer id) throws Exception;
    CategoryEntity update(CategoryEntity categoryEntity, String email) throws Exception;
    String deleteById(Integer id, String email);
}
