package com.example.user.persistence.mapper;

import com.example.user.service.dto.RequestUserDto;
import com.example.user.service.dto.ResponseUserDto;
import com.example.user.persistence.entity.CustomerEntity;

public interface UserMapper {

    ResponseUserDto userEntityToResponseUserDto(CustomerEntity customerEntity);

    CustomerEntity RequestUserDtoToGenUserEntity(RequestUserDto requestUserDto);
}
