package com.example.shop.controller;

import com.example.shop.dto.BookDTO;
import com.example.shop.entity.BookEntity;
import com.example.shop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/getAll")
    public ResponseEntity<List<BookEntity>> getAll() {
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<BookEntity> addBook (@RequestBody BookDTO bookEntity, Principal principal)throws Exception {
        return new ResponseEntity<>(bookService.createBook(bookEntity, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getBook(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(bookService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<BookEntity> updateBook(@RequestBody BookDTO bookEntity, Principal principal) throws Exception{
        return new ResponseEntity<>(bookService.update(bookEntity, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer id, Principal principal) {
        return new ResponseEntity<>(bookService.deleteById(id, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<List<BookEntity>> getByNameAll(@PathVariable("name") String name) {
        return new ResponseEntity<>(bookService.getByName(name), HttpStatus.OK);
    }

    @GetMapping("/getByLanguage/{language}")
    public ResponseEntity<List<BookEntity>> getByLanguage(@PathVariable("language") String language) {
        return new ResponseEntity<>(bookService.getByLanguage(language), HttpStatus.OK);
    }

    @GetMapping("/getByAuthor/{authorId}")
    public ResponseEntity<List<BookEntity>> getByAuthor(@PathVariable("authorId") Integer authorId) {
        return new ResponseEntity<>(bookService.getByAuthor(authorId), HttpStatus.OK);
    }

    @GetMapping("/getByCategory/{categoryId}")
    public ResponseEntity<List<BookEntity>> getByCategory(@PathVariable("categoryId") Integer categoryId) {
        return new ResponseEntity<>(bookService.getByCategory(categoryId), HttpStatus.OK);
    }

}
