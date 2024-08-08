package com.example.user.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserAlreadyExistsExceptionTest {
    @Test
    public void testUserAlreadyExistsExceptionMessage() {
        // Arrange & Act
        UserAlreadyExistsException exception = new UserAlreadyExistsException("User already exists");

        // Assert
        assertEquals("User already exists", exception.getMessage());
    }
}
