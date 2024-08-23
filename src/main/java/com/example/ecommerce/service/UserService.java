package com.example.ecommerce.service;

import com.example.ecommerce.model.dto.UserRegisterDTO;

public interface UserService {
    Boolean checkEmail(String email);
    Boolean checkPassword(String currentPassword);
    void saveUser(UserRegisterDTO userRegisterDTO);
    void saveNewPassword(String newPassword);
}
