package com.app.app_user_review.converter;

import com.app.app_user_review.UserReview;
import com.app.app_user_review.UserReviewDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserReviewDtoToUserReviewConverter implements Converter<UserReviewDto, UserReview> {
    @Override
    public UserReview convert(UserReviewDto source) {
        return new UserReview(
                source.score(),

                source.text()
        );
    }
}
