package kr.magicbox.creator.application.port.out;

import kr.magicbox.creator.application.dto.result.ReviewRating;

import java.util.concurrent.CompletableFuture;

public interface ReviewRatingQueryPort {

    CompletableFuture<ReviewRating> getReviewRating(Long creatorId);
}
