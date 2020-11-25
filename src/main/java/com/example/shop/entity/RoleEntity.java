package com.example.shop.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@NoArgsConstructor


@Entity
@Table(name = "role")
public class RoleEntity extends Base implements GrantedAuthority {

    @Column(name = "name")
    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }

    public RoleEntity(String name) {
        //this.setDeleted(false);
        this.name = name;
    }
}