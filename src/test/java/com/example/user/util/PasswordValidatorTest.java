package com.example.user.util;

import com.example.user.util.validation.PasswordValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class PasswordValidatorTest {
    @InjectMocks
    private PasswordValidator passwordValidator;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    void testValidPassword() {
        ReflectionTestUtils.setField(passwordValidator, "passwordRegex", "^[a-zA-Z0-9._%+-]{8,}$");
        String validPassword = "Valid1234";

        boolean isValid = passwordValidator.isValid(validPassword, constraintValidatorContext);

        assertThat(isValid).isTrue();
    }

    @Test
    void testInvalidPassword() {
        ReflectionTestUtils.setField(passwordValidator, "passwordRegex", "^[a-zA-Z0-9._%+-]{8,}$");
        String invalidPassword = "short";

        boolean isValid = passwordValidator.isValid(invalidPassword, constraintValidatorContext);

        assertThat(isValid).isFalse();
    }

    @Test
    void testNullPassword() {
        String nullPassword = null;

        boolean isValid = passwordValidator.isValid(nullPassword, constraintValidatorContext);

        assertThat(isValid).isFalse();
    }

}
