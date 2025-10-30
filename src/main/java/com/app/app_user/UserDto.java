package com.app.app_user;

public record UserDto(
        Long id,
        String username,
        String role,

        String fio,
        String dateReg,
        String contact,
        String city,
        String email,

        String img,
        String license,

        float rating
) {
}
