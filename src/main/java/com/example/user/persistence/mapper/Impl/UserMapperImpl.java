package com.example.user.persistence.mapper.Impl;

import com.example.user.config.JwtTokenProvider;
import com.example.user.domain.dto.PhoneDto;
import com.example.user.domain.dto.RequestUserDto;
import com.example.user.domain.dto.ResponseUserDto;
import com.example.user.persistence.entity.CustomerEntity;
import com.example.user.persistence.entity.PhoneEntity;
import com.example.user.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserMapperImpl implements UserMapper {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseUserDto userEntityToResponseUserDto(CustomerEntity customerEntity) {
        return ResponseUserDto.builder()
                .id(customerEntity.getId())
                .created(customerEntity.getCreated())
                .modified(customerEntity.getModified())
                .lastLogin(customerEntity.getLastLogin())
                .token(customerEntity.getToken())
                .isActive(customerEntity.isActive())
                .build();
    }

    @Override
    public CustomerEntity RequestUserDtoToGenUserEntity(RequestUserDto requestUserDto) {
        LocalDateTime dateTime = LocalDateTime.now();
        return CustomerEntity.builder()
                .id(UUID.randomUUID())
                .name(requestUserDto.getName())
                .email(requestUserDto.getEmail())
                .password(requestUserDto.getPassword())
                .created(dateTime)
                .modified(dateTime)
                .lastLogin(dateTime)
                .token(jwtTokenProvider.generateToken(requestUserDto.getEmail()).toString())
                .isActive(true)
                .phoneEntities(requestUserDto.getPhones().stream().map(phoneDTO -> {
                    PhoneEntity phoneEntity = new PhoneEntity();
                    phoneEntity.setNumber(phoneDTO.getNumber());
                    phoneEntity.setCitycode(phoneDTO.getCityCode());
                    phoneEntity.setCountrycode(phoneDTO.getCountryCode());
                    return phoneEntity;
                }).collect(Collectors.toList()))
                .build();
    }

}
