package com.app.app_user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ManagerDto(
        @Size(min = 1, max = 255, message = "fio is required length 1-255")
        @NotEmpty(message = "fio is required")
        String fio,
        @Size(min = 1, max = 255, message = "dateReg is required length 1-255")
        @NotEmpty(message = "dateReg is required")
        String dateReg,
        @Size(min = 1, max = 255, message = "contact is required length 1-255")
        @NotEmpty(message = "contact is required")
        String contact,
        @Size(min = 1, max = 255, message = "city is required length 1-255")
        @NotEmpty(message = "city is required")
        String city,
        @Size(min = 1, max = 255, message = "email is required length 1-255")
        @NotEmpty(message = "email is required")
        @Email(message = "email is required")
        String email
) {
}
