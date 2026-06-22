package kr.magicbox.creator.adapter.out.communication.grpc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import kr.magicbox.creator.adapter.out.communication.grpc.exception.SubscribeServiceUnavailableException;
import kr.magicbox.creator.application.port.out.SubscribeQueryPort;
import kr.magicbox.creator.grpc.subscribe.GetSubscriberCountRequest;
import kr.magicbox.creator.grpc.subscribe.IsSubscribedRequest;
import kr.magicbox.creator.grpc.subscribe.SubscribeServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscribeGrpcAdapter implements SubscribeQueryPort {

    private final SubscribeServiceGrpc.SubscribeServiceFutureStub subscribeServiceFutureStub;

    @Override
    @CircuitBreaker(name = "subscribeService", fallbackMethod = "getSubscriberCountFallback")
    @TimeLimiter(name = "subscribeService", fallbackMethod = "getSubscriberCountFallback")
    public CompletableFuture<Long> getSubscriberCount(Long creatorId) {
        return GrpcFutures.toCompletable(
                subscribeServiceFutureStub.getSubscriberCount(
                        GetSubscriberCountRequest.newBuilder().setCreatorId(creatorId).build()
                )
        ).thenApply(response -> response.getSubscriberCount());
    }

    @Override
    @CircuitBreaker(name = "subscribeService", fallbackMethod = "isSubscribedFallback")
    @TimeLimiter(name = "subscribeService", fallbackMethod = "isSubscribedFallback")
    public CompletableFuture<Boolean> isSubscribed(Long creatorId, Long userId) {
        return GrpcFutures.toCompletable(
                subscribeServiceFutureStub.isSubscribed(
                        IsSubscribedRequest.newBuilder().setCreatorId(creatorId).setUserId(userId).build()
                )
        ).thenApply(response -> response.getSubscribed());
    }

    @SuppressWarnings("unused")
    private CompletableFuture<Long> getSubscriberCountFallback(Long creatorId, Throwable throwable) {
        log.warn("구독 서비스 연결 실패");
        throw new SubscribeServiceUnavailableException(throwable);
    }

    @SuppressWarnings("unused")
    private CompletableFuture<Boolean> isSubscribedFallback(Long creatorId, Long userId, Throwable throwable) {
        log.warn("구독 서비스 연결 실패");
        throw new SubscribeServiceUnavailableException(throwable);
    }
}
