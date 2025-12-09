package com.app.app_user_review;

import com.app.app_user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserReviewService {

    private final UserReviewRepository repository;
    private final UserService userService;

    public List<UserReview> findAll(String userId) {
        return userService.find(userId).getReviewsManager();
    }

    public UserReview save(UserReview save, String managerId) {
        save.setOwner(userService.getCurrentUser());
        save.setManager(userService.find(managerId));
        return repository.save(save);
    }

}
