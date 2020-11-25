package com.example.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "author")
public class AuthorEntity extends Base{

    @Column(name = "name", length = 50)
    private String name;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "author_id", referencedColumnName = "author_id")
//    private List<BookEntity> books;


}
