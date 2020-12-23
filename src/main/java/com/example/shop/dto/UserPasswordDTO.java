package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserPasswordDTO {
    private String oldPassword;
    private String newPassword;
    private String newPassword2;
}
