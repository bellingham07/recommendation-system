package com.example.common.utils;

import com.example.common.dto.UserDto;

public class UserHolder {
    private static final ThreadLocal<UserDto> userThread = new ThreadLocal<>();

    public static void saveUser(UserDto userDto) {
        userThread.set(userDto);
    }

    public static UserDto getUser() {
        return userThread.get();
    }

    public static void removeUser() {
        userThread.remove();
    }
}
