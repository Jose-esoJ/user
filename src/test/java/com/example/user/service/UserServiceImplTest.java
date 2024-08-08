package com.example.user.service;

import com.example.user.exception.UserAlreadyExistsException;
import com.example.user.persistence.entity.CustomerEntity;
import com.example.user.persistence.mapper.UserMapper;
import com.example.user.persistence.repository.UserRepository;
import com.example.user.service.dto.RequestUserDto;
import com.example.user.service.dto.ResponseUserDto;
import com.example.user.service.service.impl.UserServiceImpl;
import com.example.user.util.constant.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void registerUser_ShouldRegisterUser_WhenEmailDoesNotExist() {
        // Arrange
        RequestUserDto requestUserDto = new RequestUserDto();
        requestUserDto.setEmail("test@example.com");

        CustomerEntity customerEntity = new CustomerEntity();
        ResponseUserDto responseUserDto = new ResponseUserDto();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userMapper.RequestUserDtoToGenUserEntity(requestUserDto)).thenReturn(customerEntity);
        when(userRepository.save(customerEntity)).thenReturn(customerEntity);
        when(userMapper.userEntityToResponseUserDto(customerEntity)).thenReturn(responseUserDto);

        // Act
        ResponseUserDto result = userService.registerUser(requestUserDto);

        // Assert
        assertThat(result).isNotNull();
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(userMapper, times(1)).RequestUserDtoToGenUserEntity(requestUserDto);
        verify(userRepository, times(1)).save(customerEntity);
        verify(userMapper, times(1)).userEntityToResponseUserDto(customerEntity);
    }

    @Test
    void registerUser_ShouldThrowException_WhenEmailAlreadyExists() {
        // Arrange
        RequestUserDto requestUserDto = new RequestUserDto();
        requestUserDto.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new CustomerEntity()));

        // Act & Assert
        assertThatThrownBy(() -> userService.registerUser(requestUserDto))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage(Message.MESSAGE_EMAIL_EXIST.getDescription());

        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(userMapper, never()).RequestUserDtoToGenUserEntity(any(RequestUserDto.class));
        verify(userRepository, never()).save(any(CustomerEntity.class));
    }
}
