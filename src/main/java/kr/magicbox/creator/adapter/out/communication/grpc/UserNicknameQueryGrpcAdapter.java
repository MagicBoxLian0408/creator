package kr.magicbox.creator.adapter.out.communication.grpc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import kr.magicbox.creator.adapter.out.communication.grpc.exception.UserServiceUnavailableException;
import kr.magicbox.creator.application.port.out.UserNicknameQueryPort;
import kr.magicbox.creator.domain.vo.UserId;
import kr.magicbox.creator.grpc.user.GetUserNicknameRequest;
import kr.magicbox.creator.grpc.user.UserServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserNicknameQueryGrpcAdapter implements UserNicknameQueryPort {

    private final UserServiceGrpc.UserServiceFutureStub userServiceFutureStub;

    @Override
    @CircuitBreaker(name = "userService", fallbackMethod = "getNicknameFallback")
    @TimeLimiter(name = "userService", fallbackMethod = "getNicknameFallback")
    public CompletableFuture<String> getNickname(UserId userId) {
        return GrpcFutures.toCompletable(
                userServiceFutureStub.getUserNickname(
                        GetUserNicknameRequest.newBuilder().setUserId(userId.value()).build()
                )
        ).thenApply(response -> response.getNickname());
    }

    @SuppressWarnings("unused")
    private CompletableFuture<String> getNicknameFallback(UserId userId, Throwable throwable) {
        log.warn("유저 서비스 연결 실패");
        throw new UserServiceUnavailableException(throwable);
    }
}
