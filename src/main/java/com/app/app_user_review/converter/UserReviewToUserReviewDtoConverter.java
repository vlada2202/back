package com.app.app_user_review.converter;

import com.app.app_user_review.UserReview;
import com.app.app_user_review.UserReviewDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserReviewToUserReviewDtoConverter implements Converter<UserReview, UserReviewDto> {
    @Override
    public UserReviewDto convert(UserReview source) {
        return new UserReviewDto(
                source.getId(),

                source.getDate(),
                source.getScore(),

                source.getText()
        );
    }
}
