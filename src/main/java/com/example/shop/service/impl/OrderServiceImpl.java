package com.example.shop.service.impl;

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
    private AddressRepository addressRepository;

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
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setAddress(addressRepository.findById(order.getAddress())
                .orElseThrow(() -> new AddressNotFoundException("Address id " + order.getAddress() + " not found")));
        orderEntity.setUser(userRepository.findByEmail(email));
        List<BookEntity> books = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Integer item : order.getBooks()) {
            BookEntity book = bookRepository.findById(item)
                    .orElseThrow(() -> new BookNotFoundException("Book id " + item + " not found"));

            if (book.getQuantity() - 1 < 0)
                throw new BookNotAvailableException("Book id " + item + " is not available now");
            book.setQuantity(book.getQuantity()-1);
            bookRepository.save(book);

            totalPrice = totalPrice.add(book.getPrice());
            books.add(book);

        }
        orderEntity.setBooks(books);
        orderEntity.setTotalPrice(totalPrice);

        orderRepository.save(orderEntity);

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("ORDER", "CREATE", user);
        historyRepository.save(history);

        return orderEntity;
    }

    @Override
    public OrderEntity findById(Integer id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order id " + id + " not found!"));
        if (order.isDeleted())
            throw  new OrderNotFoundException("Order id " + id + " not found!");
        return orderRepository.save(order);
    }

    @Override
    public OrderEntity update(Integer id, OrderDTO orderDTO, String email) {
        UserEntity user = userRepository.findByEmail(email);

        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order id " + id + " not found!"));
        if (order.isDeleted())
            throw  new OrderNotFoundException("Order id " + id + " not found!");

        order.setAddress(addressRepository.findById(orderDTO.getAddress())
                .orElseThrow(() -> new AddressNotFoundException("Order id " + orderDTO.getAddress() + " not found!")));
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
                HistoryEntity("ORDER", "UPDATE", user);
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
