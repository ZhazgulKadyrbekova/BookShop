package com.example.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "image")
public class ImageEntity extends Base {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "format", nullable = false)
    private String format;

    @Column(name = "url", nullable = false)
    private String url;

}
