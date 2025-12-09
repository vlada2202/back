package com.app.ordering;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record OrderingDto(
        Long id,

        @Size(min = 1, max = 255, message = "name is required length 1-255")
        @NotEmpty(message = "name is required")
        String name,
        @Size(min = 1, max = 255, message = "addressStart is required length 1-255")
        @NotEmpty(message = "addressStart is required")
        String addressStart,
        @Size(min = 1, max = 255, message = "addressEnd is required length 1-255")
        @NotEmpty(message = "addressEnd is required")
        String addressEnd,
        @Min(value = 1, message = "length is required min 1")
        @Max(value = 1000000, message = "length is required max 1000000")
        int length,
        @Size(min = 1, max = 255, message = "type is required length 1-255")
        @NotEmpty(message = "type is required")
        String type,
        @Min(value = 1, message = "weight is required min 1")
        @Max(value = 1000000, message = "weight is required max 1000000")
        int weight,
        @Size(min = 1, max = 255, message = "date is required length 1-255")
        @NotEmpty(message = "date is required")
        String date,
        @Min(value = 0, message = "price is required min 0")
        @Max(value = 1000000, message = "price is required max 1000000")
        float price,

        String status,
        String statusName,

        Long vehicleId,
        String vehicleName,

        Long managerId,
        String managerFio
) {
}
