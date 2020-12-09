package com.example.shop.controller;

import com.example.shop.dto.CategoryDTO;
import com.example.shop.entity.CategoryEntity;
import com.example.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController()
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryEntity>> getAll() {
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryDTO categoryEntity, Principal principal) {
        return new ResponseEntity<>(categoryService.createCategory(categoryEntity, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryEntity> findById(@PathVariable Integer id) throws Exception{
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryEntity> updateById(@PathVariable Integer id,
            @RequestBody CategoryDTO categoryEntity, Principal principal) throws Exception{
        return new ResponseEntity<>(categoryService.update(id, categoryEntity, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id, Principal principal) {
        return new ResponseEntity<>(categoryService.deleteById(id, principal.getName()), HttpStatus.OK);
    }
}
