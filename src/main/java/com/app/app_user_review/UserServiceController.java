package com.app.app_user_review;

import com.app.app_user_review.converter.UserReviewDtoToUserReviewConverter;
import com.app.app_user_review.converter.UserReviewToUserReviewDtoConverter;
import com.app.system.Result;
import com.app.system.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.app.util.Global.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/reviews")
public class UserServiceController {

    private final UserReviewService service;
    private final UserReviewToUserReviewDtoConverter toDtoConverter;
    private final UserReviewDtoToUserReviewConverter toConverter;

    @Secured({ADMIN, MANAGER, USER})
    @GetMapping("/{id}")
    public Result findAll(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All",
                service.findAll(id).stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @Secured({USER})
    @PostMapping
    public Result save(@RequestBody UserReviewDto saveDto, @RequestParam String managerId) {
        UserReview save = toConverter.convert(saveDto);
        UserReview saved = service.save(save, managerId);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Save",
                toDtoConverter.convert(saved)
        );
    }

}
