package com.example.shop.controller;

import com.example.shop.dto.AuthorDTO;
import com.example.shop.entity.AuthorEntity;
import com.example.shop.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @GetMapping("/getAll")
    public ResponseEntity<List<AuthorEntity>> getAll() {
        return new ResponseEntity<>(authorService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AuthorEntity> addAuthor(@RequestBody AuthorDTO authorEntity, Principal principal) {
        return new ResponseEntity<>(authorService.createAuthor(authorEntity, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorEntity> getAuthor(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(authorService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AuthorEntity> updateAuthor(@PathVariable Integer id,
            @RequestBody AuthorDTO authorEntity, Principal principal) {
        return new ResponseEntity<>(authorService.update(id, authorEntity, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Integer id, Principal principal) {
        return new ResponseEntity<>(authorService.deleteById(id, principal.getName()), HttpStatus.OK);
    }
}
