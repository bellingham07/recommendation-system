package com.example.common.utils.threadHolder;

import com.example.common.dto.UserDto;

public class CelebrityHolder {
    private static final ThreadLocal<UserDto> celebrityThread = new ThreadLocal<>();

    public static void saveUser(UserDto userDto) {
        celebrityThread.set(userDto);
    }

    public static UserDto getUser() {
        return celebrityThread.get();
    }

    public static void removeUser() {
        celebrityThread.remove();
    }
}
