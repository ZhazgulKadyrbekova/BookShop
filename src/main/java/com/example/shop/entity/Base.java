package com.example.shop.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data

@MappedSuperclass
public class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    @Column(name = "deleted", precision = 0, nullable = false)
    private boolean deleted;

    @PrePersist
    public void persistCreate() {
        this.dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    public void persistUpdate() {
        this.dateUpdated = LocalDateTime.now();
    }
}
