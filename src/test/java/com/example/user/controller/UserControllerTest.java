package com.example.user.controller;

import com.example.user.service.dto.RequestUserDto;
import com.example.user.service.dto.ResponseUserDto;
import com.example.user.service.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;


    @Test
    void testRegisterUserSuccess() {

        RequestUserDto requestUserDto =  RequestUserDto
                .builder()
                .email("johndoe@example.com")
                .name("John Doe")
                .password("Password123+")
                .build();

        ResponseUserDto responseUserDto = ResponseUserDto
                .builder()
                .isActive(Boolean.TRUE)
                .build();

        when(userService.registerUser(any(RequestUserDto.class))).thenReturn(responseUserDto);

        ResponseEntity<ResponseUserDto> response = userController.registerUser(requestUserDto);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void testEmailNotValidUserBadRequest() {

        RequestUserDto requestUserDto = new RequestUserDto();
        requestUserDto.setName("John Doe");
        requestUserDto.setEmail("invalid-email");
        requestUserDto.setPassword("Password123");

        when(userService.registerUser(any(RequestUserDto.class))).thenThrow(new IllegalArgumentException("Invalid email"));

        assertThrows(IllegalArgumentException.class, ()->  userController.registerUser(requestUserDto));

    }
}
