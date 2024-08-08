package com.example.user.service.service;

import com.example.user.service.dto.RequestUserDto;
import com.example.user.service.dto.ResponseUserDto;

public interface UserService {

    ResponseUserDto registerUser(RequestUserDto requestUser);
}
