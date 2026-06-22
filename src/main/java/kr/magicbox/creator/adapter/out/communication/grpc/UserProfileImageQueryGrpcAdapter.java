package kr.magicbox.creator.adapter.out.communication.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import kr.magicbox.creator.adapter.out.communication.grpc.exception.UserServiceUnavailableException;
import kr.magicbox.creator.application.port.out.UserProfileImageQueryPort;
import kr.magicbox.creator.domain.vo.UserId;
import kr.magicbox.creator.grpc.user.GetUserProfileImageUrlRequest;
import kr.magicbox.creator.grpc.user.GetUserProfileImageUrlResponse;
import kr.magicbox.creator.grpc.user.UserServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserProfileImageQueryGrpcAdapter implements UserProfileImageQueryPort {

    private final UserServiceGrpc.UserServiceFutureStub userServiceFutureStub;

    @Override
    @CircuitBreaker(name = "userService", fallbackMethod = "getProfileImageUrlFallback")
    @TimeLimiter(name = "userService", fallbackMethod = "getProfileImageUrlFallback")
    public CompletableFuture<String> getProfileImageUrl(UserId userId) {
        GetUserProfileImageUrlRequest request = GetUserProfileImageUrlRequest.newBuilder()
                .setUserId(userId.value())
                .build();

        ListenableFuture<GetUserProfileImageUrlResponse> future = userServiceFutureStub.getUserProfileImageUrl(request);

        CompletableFuture<String> result = new CompletableFuture<>();
        future.addListener(() -> {
            try {
                result.complete(future.get().getProfileImageUrl());
            } catch (Exception e) {
                result.completeExceptionally(e);
            }
        }, Runnable::run);
        return result;
    }

    @SuppressWarnings("unused")
    private CompletableFuture<String> getProfileImageUrlFallback(UserId userId, Throwable throwable) {
        log.warn("유저 서비스 연결 실패");
        throw new UserServiceUnavailableException(throwable);
    }
}
