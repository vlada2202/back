package com.app.app_user;

import com.app.app_user.converter.UserDtoToUserConverter;
import com.app.app_user.converter.UserToUserDtoConverter;
import com.app.system.Result;
import com.app.system.StatusCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

import static com.app.util.Global.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserToUserDtoConverter toDtoConverter;
    private final UserDtoToUserConverter toUserConverter;

    @Secured({ADMIN})
    @GetMapping("/all")
    public Result findAll() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All",
                service.findAll().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @GetMapping("/managers")
    public Result findAllManager() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All Managers",
                service.findAllManagers().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @Secured({ADMIN, MANAGER, USER})
    @GetMapping
    public Result find() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find",
                toDtoConverter.convert(service.getCurrentUser())
        );
    }

    @Secured({ADMIN, MANAGER, USER})
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find By Id",
                toDtoConverter.convert(service.find(id))
        );
    }

    @PostMapping
    public Result save(@Valid @RequestBody AppUser newUser) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Save",
                toDtoConverter.convert(service.save(newUser))
        );
    }

    @Secured({ADMIN, MANAGER, USER})
    @PutMapping
    public Result update(@Valid @RequestBody UserDto updateDto) {
        AppUser update = toUserConverter.convert(updateDto);
        AppUser updated = service.update(update);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update",
                toDtoConverter.convert(updated)
        );
    }

    @Secured({ADMIN})
    @PatchMapping("/{id}/role")
    public Result updateRole(@PathVariable String id, @RequestParam String role) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update Role",
                toDtoConverter.convert(service.updateRole(id, role))
        );
    }

    @Secured({USER, MANAGER})
    @PatchMapping("/img")
    public Result updateImg(@RequestParam MultipartFile files) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update Img",
                toDtoConverter.convert(service.updateImg(files))
        );
    }

    @Secured({USER, MANAGER})
    @PatchMapping("/license")
    public Result updateLicense(@RequestParam MultipartFile files) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update License",
                toDtoConverter.convert(service.updateLicense(files))
        );
    }

    @Secured({USER, MANAGER})
    @PatchMapping("/manager")
    public Result updateManager(@RequestBody ManagerDto manager) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update Manager",
                toDtoConverter.convert(service.updateManager(manager))
        );
    }

    @Secured({ADMIN})
    @PatchMapping("/{id}/approved")
    public Result updateManagerApproved(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update Manager Approved",
                toDtoConverter.convert(service.updateManagerApproved(id))
        );
    }

    @Secured({ADMIN})
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
