package com.example.ecommerce.converter;

import com.example.ecommerce.model.dto.UserRegisterDTO;
import com.example.ecommerce.model.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    @Autowired
    private ModelMapper modelMapper;

    public UserEntity userToEntity(UserRegisterDTO userRegisterDTO) {
        return modelMapper.map(userRegisterDTO, UserEntity.class);
    }
}
