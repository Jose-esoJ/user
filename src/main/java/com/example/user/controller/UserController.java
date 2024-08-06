package com.example.user.controller;

import com.example.user.domain.dto.RequestUserDto;
import com.example.user.domain.dto.ResponseUserDto;
import com.example.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUserDto> registerUser(@RequestBody RequestUserDto requestUserDto) {

        ResponseUserDto responseUserDto = userService.registerUser(requestUserDto);
            return ResponseEntity.ok(responseUserDto);
    }
}