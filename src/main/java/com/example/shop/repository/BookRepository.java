package com.example.shop.repository;

import com.example.shop.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    List<BookEntity> findAllByDeleted(boolean isDeleted);
    List<BookEntity> findAllByNameContainingIgnoringCase(String name);
    List<BookEntity> findAllByAuthorEquals(Integer author);
    List<BookEntity> findAllByLanguageContainingIgnoringCase(String language);
    List<BookEntity> findAllByCategoryEquals(Integer category);
}
