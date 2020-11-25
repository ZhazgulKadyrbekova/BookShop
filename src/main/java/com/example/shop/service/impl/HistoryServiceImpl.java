package com.example.shop.service.impl;

import com.example.shop.entity.HistoryEntity;
import com.example.shop.exception.HistoryNotFoundException;
import com.example.shop.repository.HistoryRepository;
import com.example.shop.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    HistoryRepository repository;

    @Override
    public List<HistoryEntity> getAll() {
        return repository.findAllByDeleted(false);
    }

    @Override
    public HistoryEntity findById(Integer id) {
        HistoryEntity history = repository.findById(id)
                .orElseThrow(() -> new HistoryNotFoundException("History id " + id + " not found"));
        if (history.isDeleted())
            throw new HistoryNotFoundException("History id " + id + " not found");
        return history;
    }

    @Override
    public String deleteById(Integer id, String email) {
        HistoryEntity history = repository.findById(id)
                .orElseThrow(() -> new HistoryNotFoundException("History id " + id + " not found"));
        if (history.isDeleted())
            throw new HistoryNotFoundException("History id " + id + " not found");
        history.setDeleted(true);
        repository.save(history);
        return "History id " + id + " has been deleted!";
    }
}
