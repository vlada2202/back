package com.app.vehicle.converter;

import com.app.vehicle.Vehicle;
import com.app.vehicle.VehicleDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VehicleDtoToVehicleConverter implements Converter<VehicleDto, Vehicle> {
    @Override
    public Vehicle convert(VehicleDto source) {
        return new Vehicle(
                source.name(),
                source.model(),
                source.year(),
                source.mileage(),
                source.capacity(),
                source.volume(),
                source.price(),

                source.description()
        );
    }
}
