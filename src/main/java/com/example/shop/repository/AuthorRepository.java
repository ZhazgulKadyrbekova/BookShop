package com.example.shop.repository;

import com.example.shop.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {
    List<AuthorEntity> findAllByDeleted(boolean isDeleted);
}
