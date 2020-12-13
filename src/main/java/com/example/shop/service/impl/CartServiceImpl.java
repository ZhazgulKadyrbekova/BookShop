package com.example.shop.service.impl;

import com.example.shop.dto.CartDTO;
import com.example.shop.entity.BookEntity;
import com.example.shop.entity.CartEntity;
import com.example.shop.entity.HistoryEntity;
import com.example.shop.entity.UserEntity;
import com.example.shop.exception.BookNotFoundException;
import com.example.shop.exception.CartNotFoundException;
import com.example.shop.exception.UserNotFoundException;
import com.example.shop.repository.BookRepository;
import com.example.shop.repository.CartRepository;
import com.example.shop.repository.HistoryRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public CartEntity findByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        CartEntity cart = cartRepository.findByUser(user);

        return cart;
    }

    @Override
    public CartEntity update(CartDTO cartDTO, String email) {
        UserEntity user = userRepository.findByEmail(email);

        CartEntity cart = cartRepository.findByUser(user);
        if (cart == null)
                throw new CartNotFoundException("Cart not found!");
        if (cart.isDeleted())
            throw new CartNotFoundException("Cart not found!");

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<BookEntity> books = new ArrayList<>();
        for (Integer item : cartDTO.getBooks()) {
            BookEntity book = bookRepository.findById(item)
                    .orElseThrow(() -> new BookNotFoundException("Book id " + item + " not found!"));
            totalPrice.add(book.getPrice());
            books.add(book);
        }
        cart.setBooks(books);
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);

//        HistoryEntity history = new
//                HistoryEntity("CART", "UPDATE", user);
//        historyRepository.save(history);

        return cart;
    }

}
