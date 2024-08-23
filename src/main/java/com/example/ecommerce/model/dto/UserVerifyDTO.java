package com.example.ecommerce.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVerifyDTO {
    String email;
    String password;
    String rePassword;
}
