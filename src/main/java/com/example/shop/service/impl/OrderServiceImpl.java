package com.example.shop.service.impl;

import com.example.shop.repository.OrderRepository;
import com.example.shop.dto.OrderDTO;
import com.example.shop.entity.*;
import com.example.shop.exception.*;
import com.example.shop.repository.*;
import com.example.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public List<OrderEntity> getAll() {
        return orderRepository.findAllByDeleted(false);
    }

    @Override
    public List<OrderEntity> findAllByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        return orderRepository.findAllByUser(userEntity);
    }

    @Override
    public OrderEntity createOrder(OrderDTO order, String email) {
        UserEntity user = userRepository.findByEmail(email);
        List<BookEntity> books = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Integer item : order.getBooks()) {
            BookEntity book = bookRepository.findById(item)
                    .orElseThrow(() -> new BookNotFoundException("Book id " + item + " not found"));

            if (book.getQuantity() - 1 < 0)
                throw new BookNotAvailableException("Book id " + item + " is not available now");
            book.setQuantity(book.getQuantity() - 1);
            int quant = book.getQuantity() - 1;
            book.setQuantity(quant);
            bookRepository.save(book);

            totalPrice = totalPrice.add(book.getPrice());
            books.add(book);

        }

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCity(order.getCity());
        orderEntity.setDistrict(order.getDistrict());
        orderEntity.setStreet(order.getStreet());
        orderEntity.setHouse(order.getHouse());
        orderEntity.setApartment(order.getApartment());
        orderEntity.setUser(user);
        orderEntity.setBooks(books);
        orderEntity.setTotalPrice(totalPrice);

        orderRepository.save(orderEntity);

        HistoryEntity history = new
                HistoryEntity("ORDER", "CREATE", user);
        historyRepository.save(history);

        return orderEntity;
    }

    @Override
    public OrderEntity addBooks(Integer id, OrderDTO orderDTO, String email) {
        UserEntity user = userRepository.findByEmail(email);

        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order id " + id + " not found!"));
        if (order.isDeleted())
            throw  new OrderNotFoundException("Order id " + id + " not found!");

        order.setUser(user);
        List<BookEntity> books = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Integer item : orderDTO.getBooks()) {
            BookEntity book = bookRepository.findById(item)
                    .orElseThrow(() -> new BookNotFoundException("Book id " + item + " not found"));

            if (book.getQuantity() - 1 < 0)
                throw new BookNotAvailableException("Book id " + item + " is not available now");
            book.setQuantity(book.getQuantity()-1);
            bookRepository.save(book);

            totalPrice = totalPrice.add(book.getPrice());
            books.add(book);
        }
        for (BookEntity book : order.getBooks()) {
            book.setQuantity(book.getQuantity() + 1);
            bookRepository.save(book);
        }
        order.setBooks(books);
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);

        HistoryEntity history = new
                HistoryEntity("ORDER", "UPDATE: add books", user);
        historyRepository.save(history);

        return order;
    }

    @Override
    public OrderEntity updateAddress(Integer id, OrderDTO orderEntity, String email) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order id " + id + " not found"));
        order.setCity(orderEntity.getCity());
        order.setDistrict(orderEntity.getDistrict());
        order.setStreet(orderEntity.getStreet());
        order.setHouse(orderEntity.getHouse());
        order.setApartment(orderEntity.getApartment());
        orderRepository.save(order);

        HistoryEntity history = new
                HistoryEntity("ORDER", "UPDATE: set address", userRepository.findByEmail(email));
        historyRepository.save(history);

        return order;
    }

    @Override
    public String deleteById(Integer id, String email) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order id " + id + " not found!"));
        if (order.isDeleted())
            throw  new OrderNotFoundException("Order id " + id + " not found!");

        order.setDeleted(true);
        orderRepository.save(order);

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("ORDER", "DELETE", user);
        historyRepository.save(history);

        return "Order number " + id + " has been deleted!";
    }
}
