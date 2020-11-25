package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BookDTO {
    private Integer ID;
    private String name;
    private Integer author;
    private BigDecimal price;
    private Integer pages;
    private String language;
    private Integer quantity;
    private Integer category;
}
