package com.example.user.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {
    @Test
    public void testHandleUserAlreadyExistsException() {
        // Arrange
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        UserAlreadyExistsException exception = new UserAlreadyExistsException("User already exists");

        // Act
        ResponseEntity<?> response = handler.handleUserAlreadyExistsException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"Message\": \"User already exists\"}", response.getBody());
    }
}
