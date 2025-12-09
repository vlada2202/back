package com.app.vehicle;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record VehicleDto(
        Long id,

        @Size(min = 1, max = 255, message = "name is required length 1-255")
        @NotEmpty(message = "name is required")
        String name,
        @Size(min = 1, max = 255, message = "model is required length 1-255")
        @NotEmpty(message = "model is required")
        String model,
        @Min(value = 1900, message = "year is required min 1900")
        @Max(value = 2100, message = "year is required max 2100")
        int year,
        @Min(value = 0, message = "mileage is required min 0")
        @Max(value = 1000000, message = "mileage is required max 1000000")
        int mileage,
        @Min(value = 0, message = "capacity is required min 0")
        @Max(value = 1000000, message = "capacity is required max 1000000")
        int capacity,
        @Min(value = 0, message = "volume is required min 0")
        @Max(value = 1000000, message = "volume is required max 1000000")
        int volume,
        @Min(value = 0, message = "price is required min 0")
        @Max(value = 1000000, message = "price is required max 1000000")
        float price,

        @Size(min = 1, max = 5000, message = "description is required length 1-5000")
        @NotEmpty(message = "description is required")
        String description,

        String img,

        int completed,

        Long ownerId,
        String ownerFio
) {
}
