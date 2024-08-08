package com.example.user.service.service.impl;

import com.example.user.service.dto.RequestUserDto;
import com.example.user.service.dto.ResponseUserDto;
import com.example.user.service.service.UserService;
import com.example.user.exception.UserAlreadyExistsException;
import com.example.user.persistence.entity.CustomerEntity;
import com.example.user.persistence.mapper.UserMapper;
import com.example.user.persistence.repository.UserRepository;
import com.example.user.util.constant.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public ResponseUserDto registerUser(RequestUserDto requestUser) {

        validateEmailExist(requestUser.getEmail());
        CustomerEntity customerEntity = userMapper.RequestUserDtoToGenUserEntity(requestUser);
        userRepository.save(customerEntity);
        return userMapper.userEntityToResponseUserDto(customerEntity);

    }

    private void validateEmailExist(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException(Message.MESSAGE_EMAIL_EXIST.getDescription());
        }
    }



}
