package com.example.shop.service.impl;

import com.example.shop.entity.CategoryEntity;
import com.example.shop.entity.HistoryEntity;
import com.example.shop.entity.UserEntity;
import com.example.shop.exception.CategoryNotFoundException;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.repository.HistoryRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.CategoryService;
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
    public CategoryEntity createCategory(CategoryEntity categoryEntity, String email) {
        CategoryEntity category = new CategoryEntity();
        category.setName(category.getName());

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("CATEGORY", "CREATE id:" + category.getID().toString(), user);
        historyRepository.save(history);

        return categoryRepository.save(category);
    }

    @Override
    public CategoryEntity findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category id " + id + " not found!"));
    }

    @Override
    public CategoryEntity update(CategoryEntity categoryEntity, String email) {
        CategoryEntity category = categoryRepository.findById(categoryEntity.getID())
                .orElseThrow(() -> new CategoryNotFoundException("Category id " + categoryEntity.getID() + " not found!"));
        if (category.isDeleted()) {
            throw new CategoryNotFoundException("Category id " + categoryEntity.getID() + " not found!");
        }
        category.setName(categoryEntity.getName());

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("CATEGORY", "UPDATE id:" + categoryEntity.getID().toString(), user);
        historyRepository.save(history);

        return categoryRepository.save(category);
    }

    @Override
    public String deleteById(Integer id, String email) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category id " + id + " not found!"));
        if (category.isDeleted()) {
            throw new CategoryNotFoundException("Category id " + id + " not found!");
        }
        category.setDeleted(true);

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("CATEGORY", "DELETE id:" + category.getID().toString(), user);
        historyRepository.save(history);

        return "Category number " + id + " has been deleted!";
    }
}
