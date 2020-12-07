package com.example.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "address")
public class AddressEntity extends Base{

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
}
