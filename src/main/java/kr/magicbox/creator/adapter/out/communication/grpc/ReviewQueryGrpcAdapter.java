package kr.magicbox.creator.adapter.out.communication.grpc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.grpc.ManagedChannel;
import kr.magicbox.creator.adapter.out.communication.ServiceHost;
import kr.magicbox.creator.adapter.out.communication.grpc.exception.ReviewServiceUnavailableException;
import kr.magicbox.creator.application.dto.result.ReviewRating;
import kr.magicbox.creator.application.port.out.ReviewRatingQueryPort;
import kr.magicbox.creator.grpc.review.GetReviewRatingRequest;
import kr.magicbox.creator.grpc.review.GetReviewRatingResponse;
import kr.magicbox.creator.grpc.review.ReviewServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewQueryGrpcAdapter implements ReviewRatingQueryPort {
    private final GrpcChannelFactory grpcChannelFactory;

    @Override
    @CircuitBreaker(name = "reviewService", fallbackMethod = "getReviewRatingFallback")
    public ReviewRating getReviewRating(Long creatorId) {
        GetReviewRatingRequest request = GetReviewRatingRequest.newBuilder()
                .setCreatorId(creatorId)
                .build();

        ManagedChannel channel = grpcChannelFactory.createChannel(ServiceHost.REVIEW.getHostName());
        ReviewServiceGrpc.ReviewServiceBlockingStub stub = ReviewServiceGrpc
                .newBlockingStub(channel)
                .withDeadlineAfter(2, TimeUnit.SECONDS);
        GetReviewRatingResponse response = stub.getReviewRating(request);

        return ReviewRating.of(response.getRating());
    }

    @SuppressWarnings("unused")
    private ReviewRating getReviewRatingFallback(Long creatorId, Throwable throwable) {
        log.warn("리뷰 서비스 연결 실패");
        throw new ReviewServiceUnavailableException(throwable);
    }
}