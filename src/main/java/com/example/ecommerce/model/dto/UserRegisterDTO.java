package com.example.ecommerce.model.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserRegisterDTO {

    @NotBlank(message = "Full name is require !")
    private String name;

    @NotBlank(message = "Email is require !")
    private String email;

    @NotBlank(message = "Password is require !")
    private String password;

    @NotBlank(message = "Remind password is require !")
    private String rePassword;

    @NotBlank(message = "Current Password is require !")
    private String currentPassword;

}
