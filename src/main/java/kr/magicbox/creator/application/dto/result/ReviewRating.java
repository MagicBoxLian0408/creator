package kr.magicbox.creator.application.dto.result;

import kr.magicbox.creator.domain.exception.InvalidReviewRatingException;

public record ReviewRating(double value) {

    public ReviewRating {
        if (value < 0.0 || value > 5.0) {
            throw new InvalidReviewRatingException();
        }
    }

    public static ReviewRating of(double value) {
        return new ReviewRating(value);
    }

    public static ReviewRating zero() {
        return new ReviewRating(0.0);
    }
}
