package com.app.vehicle.converter;

import com.app.vehicle.Vehicle;
import com.app.vehicle.VehicleDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VehicleToVehicleDtoConverter implements Converter<Vehicle, VehicleDto> {
    @Override
    public VehicleDto convert(Vehicle source) {
        return new VehicleDto(
                source.getId(),

                source.getName(),
                source.getModel(),
                source.getYear(),
                source.getMileage(),
                source.getCapacity(),
                source.getVolume(),
                source.getPrice(),

                source.getDescription(),

                source.getImg(),

                source.getCompleted(),

                source.getOwner().getId(),
                source.getOwner().getFio()
        );
    }
}
