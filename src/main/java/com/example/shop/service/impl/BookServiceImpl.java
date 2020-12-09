package com.example.shop.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.shop.dto.BookDTO;
import com.example.shop.entity.BookEntity;
import com.example.shop.entity.HistoryEntity;
import com.example.shop.entity.ImageEntity;
import com.example.shop.entity.UserEntity;
import com.example.shop.exception.AuthorNotFoundException;
import com.example.shop.exception.BookNotFoundException;
import com.example.shop.exception.CategoryNotFoundException;
import com.example.shop.repository.*;
import com.example.shop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<BookEntity> getAll() {
        return bookRepository.findAllByDeleted(false);
    }

    @Override
    public BookEntity createBook(BookDTO book, String email) {
        BookEntity newBook = new BookEntity();
        newBook.setName(book.getName());
        newBook.setAuthor(authorRepository.findById(book.getAuthor())
                .orElseThrow(() -> new AuthorNotFoundException("Author id " + book.getAuthor() + " not found!")));
        newBook.setPrice(book.getPrice());
        newBook.setPages(book.getPages());
        newBook.setLanguage(book.getLanguage());
        newBook.setQuantity(book.getQuantity());
        newBook.setCategory(categoryRepository.findById(book.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Category id " + book.getCategory() + " not found!")));
        newBook.setDeleted(false);

        bookRepository.save(newBook);

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("BOOK", "CREATE " + book.getName(), user);
        historyRepository.save(history);

        return newBook;
    }

    @Override
    public BookEntity findById(Integer id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book id " + id + " not found!"));
        if (book.isDeleted())
            throw new BookNotFoundException("Book id " + id + " not found!");
        return book;
    }

    @Override
    public BookEntity update(Integer id, BookDTO bookDTO, String email) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book id " + id + " not found!"));
        if (book.isDeleted())
            throw new BookNotFoundException("Book id " + id + " not found!");

        book.setName(book.getName());
        book.setAuthor(authorRepository.findById(bookDTO.getAuthor())
                .orElseThrow(() -> new AuthorNotFoundException("Author id " + bookDTO.getAuthor() + " not found!")));
        book.setPrice(book.getPrice());
        book.setPages(book.getPages());
        book.setLanguage(book.getLanguage());
        book.setQuantity(book.getQuantity());
        book.setCategory(categoryRepository.findById(bookDTO.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Category id " + bookDTO.getCategory() + " not found!")));
        book.setDeleted(false);

        bookRepository.save(book);
        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("BOOK", "UPDATE " + book.getName(), user);
        historyRepository.save(history);

        return book;
    }

    @Override
    public BookEntity setImage(MultipartFile multipartFile, String email, Integer bookId) throws IOException {
        final String urlKey = "cloudinary://122578963631996:RKDo37y7ru4nnuLsBGQbwBUk65o@zhazgul/";

        ImageEntity image = new ImageEntity();
        File file;
        try {
            BookEntity book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new BookNotFoundException("Book id " + bookId + " not found"));

            file = Files.createTempFile(System.currentTimeMillis() + "",
                    multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().length()-4))
                    .toFile();
            multipartFile.transferTo(file);

            Cloudinary cloudinary = new Cloudinary(urlKey);
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            image.setName((String) uploadResult.get("public_id"));
            image.setUrl((String) uploadResult.get("url"));
            image.setFormat((String) uploadResult.get("format"));
            imageRepository.save(image);

            book.setImage(image);

            bookRepository.save(book);

            UserEntity user = userRepository.findByEmail(email);
            HistoryEntity history = new
                    HistoryEntity("BOOK", "SET IMAGE " + book.getName(), user);
            historyRepository.save(history);

            return book;

        } catch (IOException e) {
            throw new IOException("Unable to set an image to book");
        }

    }

    @Override
    public String deleteById(Integer id, String email) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book id " + id + " not found!"));
        if (book.isDeleted())
            throw new BookNotFoundException("Book id " + id + " not found!");

        book.setDeleted(true);
        bookRepository.save(book);

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("BOOK", "DELETE " + book.getName(), user);
        historyRepository.save(history);

        return "Book number " + id + " has been deleted!";
    }

    @Override
    public List<BookEntity> getByName(String name) {
        return bookRepository.findAllByNameContainingIgnoringCase(name);
    }

    @Override
    public List<BookEntity> getByAuthor(Integer author) {
        return bookRepository.findAllByAuthorEquals(author);
    }

    @Override
    public List<BookEntity> getByLanguage(String language) {
        return bookRepository.findAllByLanguageContainingIgnoringCase(language);
    }

    @Override
    public List<BookEntity> getByCategory(Integer category) {
        return bookRepository.findAllByCategoryEquals(category);
    }
}
