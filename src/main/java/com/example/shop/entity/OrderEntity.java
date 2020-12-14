package com.example.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "`order`")
public class OrderEntity extends Base{

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "house_number", nullable = false)
    private String house;

    @Column(name = "apartment_number")
    private String apartment;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToMany
    @JoinTable(name = "order_book", joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")},
            inverseJoinColumns = { @JoinColumn(name = "book_id", referencedColumnName = "id")})
    private List<BookEntity> books;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

}
