package com.example.ecommerce.service.impl;

import com.example.ecommerce.converter.UserConverter;
import com.example.ecommerce.model.dto.UserRegisterDTO;
import com.example.ecommerce.model.entity.UserEntity;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityUtils securityUtils;


    @Override
    public Boolean checkEmail(String email) {
        if(userRepository.findByEmail(email).orElse(null) == null){
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkPassword(String currentPassword) {
        // Lấy user từ cơ sở dữ liệu dựa trên email
        UserEntity userEntity = userRepository.findByEmail(securityUtils.getCurrentUsername()).orElse(null);

        // Kiểm tra nếu userEntity không null
        // Sử dụng PasswordEncoder để so sánh mật khẩu nhập vào với mật khẩu trong cơ sở dữ liệu
        return passwordEncoder.matches(currentPassword, userEntity.getPassword());
    }

    @Override
    public void saveUser(UserRegisterDTO userRegisterDTO) {
        UserEntity userEntity= userConverter.userToEntity(userRegisterDTO);
        userEntity.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public void saveNewPassword(String newPassword) {
        UserEntity userEntity = userRepository.findByEmail(securityUtils.getCurrentUsername()).orElse(null);
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userEntity);
    }
}
