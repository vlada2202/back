package com.app.vehicle;

import com.app.system.Result;
import com.app.system.StatusCode;
import com.app.vehicle.converter.VehicleDtoToVehicleConverter;
import com.app.vehicle.converter.VehicleToVehicleDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

import static com.app.util.Global.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService service;
    private final VehicleDtoToVehicleConverter toConverter;
    private final VehicleToVehicleDtoConverter toDtoConverter;

    @GetMapping
    public Result findAll() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All",
                service.findAll().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @GetMapping("/manager/{id}")
    public Result findAllManager(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All Manager",
                service.findAllManager(id).stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @Secured({ADMIN, MANAGER, USER})
    @GetMapping("/{id}")
    public Result find(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find",
                toDtoConverter.convert(service.find(id))
        );
    }

    @Secured({MANAGER})
    @PostMapping
    public Result save(@RequestBody VehicleDto saveDto) {
        Vehicle save = toConverter.convert(saveDto);
        Vehicle saved = service.save(save);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Save",
                toDtoConverter.convert(saved)
        );
    }

    @Secured({MANAGER})
    @PutMapping("/{id}")
    public Result update(@PathVariable String id, @RequestBody VehicleDto updateDto) {
        Vehicle update = toConverter.convert(updateDto);
        Vehicle updated = service.update(id, update);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update",
                toDtoConverter.convert(updated)
        );
    }

    @Secured({MANAGER})
    @PatchMapping("/{id}/img")
    public Result updateImg(@PathVariable String id, @RequestParam MultipartFile files) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update Img",
                toDtoConverter.convert(service.updateImg(id, files))
        );
    }

    @Secured({MANAGER})
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        service.delete(id);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Delete"
        );
    }

}
