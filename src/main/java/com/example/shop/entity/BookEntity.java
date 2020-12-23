package com.example.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "book")
public class BookEntity extends Base{

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private AuthorEntity author;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "pages")
    private int pages;

    @Column(name = "language", length = 3)
    private String language;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private ImageEntity image;
}
