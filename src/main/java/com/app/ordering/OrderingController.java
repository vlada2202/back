package com.app.ordering;

import com.app.ordering.converter.OrderingDtoToOrderingConverter;
import com.app.ordering.converter.OrderingToOrderingDtoConverter;
import com.app.system.Result;
import com.app.system.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.app.util.Global.MANAGER;
import static com.app.util.Global.USER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderings")
public class OrderingController {

    private final OrderingService service;
    private final OrderingToOrderingDtoConverter toDtoConverter;
    private final OrderingDtoToOrderingConverter toConverter;

    @Secured({MANAGER, USER})
    @GetMapping
    public Result findAll() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All",
                service.findAll().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @Secured({MANAGER, USER})
    @GetMapping("/{id}")
    public Result find(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find",
                toDtoConverter.convert(service.find(id))
        );
    }

    @Secured({USER})
    @PostMapping
    public Result save(@RequestBody OrderingDto saveDto, @RequestParam String vehicleId) {
        Ordering save = toConverter.convert(saveDto);
        Ordering saved = service.save(save, vehicleId);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Save",
                toDtoConverter.convert(saved)
        );
    }

    @Secured({MANAGER})
    @PatchMapping("/{id}/approved")
    public Result approved(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Approved",
                toDtoConverter.convert(service.approved(id))
        );
    }

    @Secured({MANAGER})
    @PatchMapping("/{id}/rejected")
    public Result rejected(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Rejected",
                toDtoConverter.convert(service.rejected(id))
        );
    }

    @Secured({MANAGER})
    @PatchMapping("/{id}/done")
    public Result done(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Done",
                toDtoConverter.convert(service.done(id))
        );
    }

}
