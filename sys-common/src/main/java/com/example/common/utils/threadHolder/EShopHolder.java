package com.example.common.utils.threadHolder;

import com.example.common.dto.UserDto;

public class EShopHolder {
    private static final ThreadLocal<UserDto> eshopThread = new ThreadLocal<>();

    public static void saveUser(UserDto userDto) {
        eshopThread.set(userDto);
    }

    public static UserDto getUser() {
        return eshopThread.get();
    }

    public static void removeUser() {
        eshopThread.remove();
    }
}
