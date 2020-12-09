package com.example.shop.service;

import com.example.shop.dto.BookDTO;
import com.example.shop.entity.BookEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {
    List<BookEntity> getAll();
    BookEntity createBook(BookDTO book, String email) throws Exception;
    BookEntity findById(Integer id) throws Exception;
    BookEntity update(Integer id, BookDTO book, String email) throws Exception;
    BookEntity setImage(MultipartFile multipartFile, String email, Integer bookId) throws IOException;
    String deleteById(Integer id, String email);
    List<BookEntity> getByName(String name);
    List<BookEntity> getByAuthor(Integer authorId);
    List<BookEntity> getByLanguage(String language);
    List<BookEntity> getByCategory(Integer categoryId);
}
