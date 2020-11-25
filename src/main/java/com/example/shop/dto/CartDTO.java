package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CartDTO {
    private Integer id;
    private Integer user;
    private List<Integer> books;
    private BigDecimal totalPrice;
}
