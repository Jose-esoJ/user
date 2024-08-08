package com.example.user.util.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Message {
    MESSAGE_EMAIL_EXIST("The Email is already registered");

    private final String description;
}
