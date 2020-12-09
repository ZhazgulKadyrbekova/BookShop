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
@Table(name = "cart")
public class CartEntity extends Base{

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private List<BookEntity> books;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

//    @OneToMany
//    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
//    private List<OrderEntity> order;

}
