package com.example.shop.service;

import com.example.shop.dto.AuthorDTO;
import com.example.shop.entity.AuthorEntity;

import java.util.List;

public interface AuthorService {
    List<AuthorEntity> getAll();
    AuthorEntity createAuthor (AuthorDTO author, String email);
    AuthorEntity findById(Integer id);
    AuthorEntity update(Integer id, AuthorDTO author, String email);
    String deleteById(Integer id, String email);
}
