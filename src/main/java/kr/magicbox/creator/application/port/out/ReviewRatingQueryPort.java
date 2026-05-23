package kr.magicbox.creator.application.port.out;

import kr.magicbox.creator.application.dto.result.ReviewRating;

public interface ReviewRatingQueryPort {

    ReviewRating getReviewRating(Long creatorId);
}
