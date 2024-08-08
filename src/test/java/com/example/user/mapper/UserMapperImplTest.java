package com.example.user.mapper;

import com.example.user.config.security.JwtTokenProvider;
import com.example.user.persistence.entity.CustomerEntity;
import com.example.user.persistence.entity.PhoneEntity;
import com.example.user.persistence.mapper.Impl.UserMapperImpl;
import com.example.user.service.dto.PhoneDto;
import com.example.user.service.dto.RequestUserDto;
import com.example.user.service.dto.ResponseUserDto;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMapperImplTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserMapperImpl userMapper;

    @Test
    void testUserEntityToResponseUserDto() {
        UUID userId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        CustomerEntity customerEntity = CustomerEntity.builder()
                .id(userId)
                .created(now)
                .modified(now)
                .lastLogin(now)
                .token("sample-token")
                .isActive(true)
                .build();

        ResponseUserDto responseUserDto = userMapper.userEntityToResponseUserDto(customerEntity);

        assertThat(responseUserDto).isNotNull();
        assertThat(responseUserDto.getId()).isEqualTo(userId);
        assertThat(responseUserDto.getCreated()).isEqualTo(now);
        assertThat(responseUserDto.getModified()).isEqualTo(now);
        assertThat(responseUserDto.getLastLogin()).isEqualTo(now);
        assertThat(responseUserDto.getToken()).isEqualTo("sample-token");
        assertThat(responseUserDto.isActive()).isTrue();
    }

    @Test
    void testRequestUserDtoToGenUserEntity() {

        String email = "test@example.com";
        String token = "generated-token";
        RequestUserDto requestUserDto = RequestUserDto.builder()
                .name("John Doe")
                .email(email)
                .password("password123")
                .phones(Collections.singletonList(PhoneDto.builder()
                        .number("123456789")
                        .cityCode("01")
                        .countryCode("91")
                        .build()))
                .build();

        when(jwtTokenProvider.generateToken(email)).thenReturn(token);

        CustomerEntity customerEntity = userMapper.RequestUserDtoToGenUserEntity(requestUserDto);

        assertThat(customerEntity).isNotNull();
        assertThat(customerEntity.getId()).isNotNull();
        assertThat(customerEntity.getName()).isEqualTo("John Doe");
        assertThat(customerEntity.getEmail()).isEqualTo(email);
        assertThat(customerEntity.getPassword()).isEqualTo("password123");
        assertThat(customerEntity.getCreated()).isNotNull();
        assertThat(customerEntity.getModified()).isNotNull();
        assertThat(customerEntity.getLastLogin()).isNotNull();
        assertThat(customerEntity.getToken()).isEqualTo(token);
        assertThat(customerEntity.isActive()).isTrue();
        assertThat(customerEntity.getPhoneEntities()).hasSize(1);

        PhoneEntity phoneEntity = customerEntity.getPhoneEntities().get(0);
        assertThat(phoneEntity.getNumber()).isEqualTo("123456789");
        assertThat(phoneEntity.getCitycode()).isEqualTo("01");
        assertThat(phoneEntity.getCountrycode()).isEqualTo("91");
    }
}
