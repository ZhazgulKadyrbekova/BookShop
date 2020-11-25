package com.example.shop.service;

import com.example.shop.dto.BookDTO;
import com.example.shop.entity.BookEntity;

import java.util.List;

public interface BookService {
    List<BookEntity> getAll();
    BookEntity createBook(BookDTO book, String email) throws Exception;
    BookEntity findById(Integer id) throws Exception;
    BookEntity update(BookDTO book, String email) throws Exception;
    String deleteById(Integer id, String email);
    List<BookEntity> getByName(String name);
    List<BookEntity> getByAuthor(Integer authorId);
    List<BookEntity> getByLanguage(String language);
    List<BookEntity> getByCategory(Integer categoryId);
}
