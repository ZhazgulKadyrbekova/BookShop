package com.example.shop.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data

@MappedSuperclass
public class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    @Column(name = "deleted", precision = 0, nullable = false)
    private boolean deleted;

    @PrePersist
    public void persistCreate() {
        this.dateCreated = LocalDate.now();
    }

    @PreUpdate
    public void persistUpdate() {
        this.dateUpdated = LocalDate.now();
    }
}
