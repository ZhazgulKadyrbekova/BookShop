package com.example.shop.service.impl;

import com.example.shop.dto.CategoryDTO;
import com.example.shop.entity.CategoryEntity;
import com.example.shop.entity.HistoryEntity;
import com.example.shop.entity.UserEntity;
import com.example.shop.exception.CategoryNotFoundException;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.repository.HistoryRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.CategoryService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CategoryEntity> getAll() {
        return categoryRepository.findAllByDeleted(false);
    }

    @Override
    public CategoryEntity createCategory(CategoryDTO categoryEntity, String email) {
        CategoryEntity category = new CategoryEntity();
        category.setName(categoryEntity.getName());
        categoryRepository.save(category);

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("CATEGORY", "CREATE " + category.getName(), user);
        historyRepository.save(history);

        return category;
    }

    @Override
    public CategoryEntity findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category id " + id + " not found!"));
    }

    @Override
    public CategoryEntity update(Integer id, CategoryDTO categoryEntity, String email) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category id " + id + " not found!"));
        if (category.isDeleted()) {
            throw new CategoryNotFoundException("Category id " + id + " not found!");
        }
        category.setName(categoryEntity.getName());
        categoryRepository.save(category);

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("CATEGORY", "UPDATE " + categoryEntity.getName(), user);
        historyRepository.save(history);

        return category;
    }

    @Override
    public String deleteById(Integer id, String email) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category id " + id + " not found!"));
        if (category.isDeleted()) {
            throw new CategoryNotFoundException("Category id " + id + " not found!");
        }
        category.setDeleted(true);
        categoryRepository.save(category);

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("CATEGORY", "DELETE " + category.getName(), user);
        historyRepository.save(history);

        return "Category number " + id + " has been deleted!";
    }
}
