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
    public List<CartEntity> getAll() {
        return cartRepository.findAllByDeleted(false);
    }

    @Override
    public CartEntity createCart(CartDTO cartEntity, String email) throws Exception {
        CartEntity cart = new CartEntity();
        cart.setTotalPrice(cartEntity.getTotalPrice());
        cart.setUser(userRepository.findById(cartEntity.getUser()).orElseThrow(Exception::new));
        List<BookEntity> books = new ArrayList<>();
        for (Integer item : cartEntity.getBooks()) {
            books.add(bookRepository.findById(item).orElseThrow(Exception::new));
        }
        cart.setBooks(books);
        cartRepository.save(cart);

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("CART", "CREATE", user);
        historyRepository.save(history);

        return cart;
    }

    @Override
    public CartEntity findById(Integer id) {
        CartEntity cart = cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart id " + id + " not found!"));
        if (cart.isDeleted())
            throw new CartNotFoundException("Cart id " + id + " not found!");
        return cart;
    }

    @Override
    public CartEntity update(Integer id, CartDTO cartEntity, String email) {
        CartEntity cart = cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart id " + id + " not found!"));
        if (cart.isDeleted())
            throw new CartNotFoundException("Cart id " + id + " not found!");

        cart.setTotalPrice(cartEntity.getTotalPrice());
        cart.setUser(userRepository.findById(cartEntity.getUser())
                .orElseThrow(() -> new UserNotFoundException("User id " + cartEntity.getUser() + " not found!")));
        List<BookEntity> books = new ArrayList<>();
        for (Integer item : cartEntity.getBooks()) {
            books.add(bookRepository.findById(item)
                    .orElseThrow(() -> new BookNotFoundException("Book id " + item + " not found!")));
        }
        cart.setBooks(books);
        cartRepository.save(cart);

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("CART", "UPDATE", user);
        historyRepository.save(history);

        return cart;
    }

    @Override
    public String deleteById(Integer id, String email) {
        CartEntity cart = cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart id " + id + " not found!"));
        if (cart.isDeleted())
            throw new CartNotFoundException("Cart id " + id + " not found!");

        cart.setDeleted(true);
        cartRepository.save(cart);

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("CART", "DELETE", user);
        historyRepository.save(history);

        return "Cart number " + id + " has been deleted!";
    }
}
