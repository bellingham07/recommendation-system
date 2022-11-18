package com.example.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {
    private String phonenumber;
    private String tel;
    private String passwordRaw;
    private String password1;
    private String password2;
}
