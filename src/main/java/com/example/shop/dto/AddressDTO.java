package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AddressDTO {
    private String city;
    private String district;
    private String street;
    private String house;
    private String apartment;

}
