package com.example.shop.repository;

import com.example.shop.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Integer> {
    List<HistoryEntity> findAllByDeleted(boolean isDeleted);

}
