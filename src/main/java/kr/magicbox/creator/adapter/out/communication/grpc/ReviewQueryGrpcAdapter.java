package kr.magicbox.creator.adapter.out.communication.grpc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import kr.magicbox.creator.adapter.out.communication.grpc.exception.ReviewServiceUnavailableException;
import kr.magicbox.creator.application.dto.result.ReviewRating;
import kr.magicbox.creator.application.port.out.ReviewRatingQueryPort;
import kr.magicbox.creator.grpc.review.GetReviewRatingRequest;
import kr.magicbox.creator.grpc.review.ReviewServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewQueryGrpcAdapter implements ReviewRatingQueryPort {

    private final ReviewServiceGrpc.ReviewServiceFutureStub reviewServiceFutureStub;

    @Override
    @CircuitBreaker(name = "reviewService", fallbackMethod = "getReviewRatingFallback")
    @TimeLimiter(name = "reviewService", fallbackMethod = "getReviewRatingFallback")
    public CompletableFuture<ReviewRating> getReviewRating(Long creatorId) {
        return GrpcFutures.toCompletable(
                reviewServiceFutureStub.getReviewRating(
                        GetReviewRatingRequest.newBuilder().setCreatorId(creatorId).build()
                )
        ).thenApply(response -> ReviewRating.of(response.getRating()));
    }

    @SuppressWarnings("unused")
    private CompletableFuture<ReviewRating> getReviewRatingFallback(Long creatorId, Throwable throwable) {
        log.warn("리뷰 서비스 연결 실패");
        throw new ReviewServiceUnavailableException(throwable);
    }
}
