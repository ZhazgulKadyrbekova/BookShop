package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderDTO {
    private String city;
    private String district;
    private String street;
    private String house;
    private String apartment;
    private List<Integer> books;
}
