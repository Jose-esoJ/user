package com.example.user.domain.service;

import com.example.user.domain.dto.RequestUserDto;
import com.example.user.domain.dto.ResponseUserDto;

public interface UserService {

    ResponseUserDto registerUser(RequestUserDto requestUser);
}
