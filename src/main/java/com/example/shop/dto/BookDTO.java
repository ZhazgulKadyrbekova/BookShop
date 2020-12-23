package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BookDTO {
    private String name;
    private Integer author;
    private BigDecimal price;
    private int pages;
    private String language;
    private int quantity;
    private Integer category;
}
