package com.example.shop.service.impl;

import com.example.shop.entity.AuthorEntity;
import com.example.shop.entity.HistoryEntity;
import com.example.shop.entity.UserEntity;
import com.example.shop.exception.AuthorNotFoundException;
import com.example.shop.exception.UserNotFoundException;
import com.example.shop.repository.AuthorRepository;
import com.example.shop.repository.HistoryRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<AuthorEntity> getAll() {
        return authorRepository.findAllByDeleted(false);
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity author, String email) {

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName(author.getName());

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("AUTHOR", "CREATE " + authorEntity.getName(), user);
        historyRepository.save(history);

        return authorRepository.save(authorEntity);
    }

    @Override
    public AuthorEntity findById(Integer id) {
        AuthorEntity author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author id " + id + " not found!"));
        if (author.isDeleted()) {
            throw new AuthorNotFoundException("Author id " + id + " not found!");
        }
        return author;
    }

    @Override
    public AuthorEntity update(AuthorEntity author, String email) {
        AuthorEntity authorEntity = authorRepository.findById(author.getID())
                .orElseThrow(() -> new AuthorNotFoundException("Author id " + author.getID() + " not found!"));
        if (author.isDeleted()) {
            throw new AuthorNotFoundException("Author id " + author.getID() + " not found!");
        }
        authorEntity.setName(author.getName());

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("AUTHOR", "UPDATE " + author.getName(), user);
        historyRepository.save(history);

        return  authorRepository.save(authorEntity);
    }

    @Override
    public String deleteById(Integer id, String email) {
        AuthorEntity author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author id " + id + " not found!"));
        if (author.isDeleted()) {
            throw new AuthorNotFoundException("Author id " + id + " not found!");
        }
        author.setDeleted(true);

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("AUTHOR", "DELETE " + author.getName(), user);
        historyRepository.save(history);

        return "Author id " + id + "has been successfully deleted!";
    }
}
