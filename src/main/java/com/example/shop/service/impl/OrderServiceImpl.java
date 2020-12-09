package com.example.shop.service.impl;

import com.example.shop.dto.OrderDTO;
import com.example.shop.entity.*;
import com.example.shop.exception.AddressNotFoundException;
import com.example.shop.exception.BookNotFoundException;
import com.example.shop.exception.OrderNotFoundException;
import com.example.shop.exception.UserNotFoundException;
import com.example.shop.repository.*;
import com.example.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public OrderEntity createOrder(OrderDTO order, String email) throws Exception{
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setAddress(addressRepository.findById(order.getAddress()).orElseThrow(Exception::new));
        orderEntity.setUser(userRepository.findById(order.getUser()).orElseThrow(Exception::new));
        List<BookEntity> books = new ArrayList<>();
        for (Integer item : order.getBooks()) {
            books.add(bookRepository.findById(item).orElseThrow(Exception::new));
        }
        orderEntity.setBooks(books);
        orderEntity.setTotalPrice(order.getTotalPrice());
        orderEntity.setDate(LocalDate.now());

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("ORDER", "CREATE", user);
        historyRepository.save(history);

        return orderRepository.save(orderEntity);
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
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order id " + id + " not found!"));
        if (order.isDeleted())
            throw  new OrderNotFoundException("Order id " + id + " not found!");

        order.setAddress(addressRepository.findById(orderDTO.getAddress())
                .orElseThrow(() -> new AddressNotFoundException("Order id " + orderDTO.getAddress() + " not found!")));
        order.setUser(userRepository.findById(orderDTO.getUser())
                .orElseThrow(() -> new UserNotFoundException("Order id " + orderDTO.getUser() + " not found!")));
        List<BookEntity> books = new ArrayList<>();
        for (Integer item : orderDTO.getBooks()) {
            books.add(bookRepository.findById(item)
                    .orElseThrow(() -> new BookNotFoundException("Order id " + item + " not found!")));
        }
        order.setBooks(books);
        order.setTotalPrice(order.getTotalPrice());

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("ORDER", "UPDATE", user);
        historyRepository.save(history);

        return orderRepository.save(order);
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
