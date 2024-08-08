package com.example.user.controller;

import com.example.user.service.dto.RequestUserDto;
import com.example.user.service.dto.ResponseUserDto;
import com.example.user.service.service.UserService;
import com.example.user.util.constant.ApiPaths;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = ApiPaths.USERS)
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Email already registered or invalid data")
    })
    @PostMapping(value = ApiPaths.REGISTER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUserDto> registerUser(@Valid @RequestBody RequestUserDto requestUserDto) {

        ResponseUserDto responseUserDto = userService.registerUser(requestUserDto);
            return ResponseEntity.ok(responseUserDto);
    }
}