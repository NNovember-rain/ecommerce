package com.example.ecommerce.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserPasswordDTO {

    @NotBlank(message = "New password is require !")
    private String password;

    @NotBlank(message = "Repeat new password is require !")
    private String rePassword;

    @NotBlank(message = "Current Password is require !")
    private String currentPassword;

}