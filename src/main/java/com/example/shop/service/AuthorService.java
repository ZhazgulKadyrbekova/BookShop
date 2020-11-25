package com.example.shop.service;

import com.example.shop.entity.AuthorEntity;

import java.util.List;

public interface AuthorService {
    List<AuthorEntity> getAll();
    AuthorEntity createAuthor (AuthorEntity author, String email);
    AuthorEntity findById(Integer id) throws Exception;
    AuthorEntity update(AuthorEntity author, String email) throws Exception;
    String deleteById(Integer id, String email);
}
