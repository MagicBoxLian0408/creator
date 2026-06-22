package kr.magicbox.creator.adapter.out.communication.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import kr.magicbox.creator.adapter.out.communication.grpc.exception.SubscribeServiceUnavailableException;
import kr.magicbox.creator.application.port.out.SubscribeQueryPort;
import kr.magicbox.creator.grpc.subscribe.GetSubscriberCountRequest;
import kr.magicbox.creator.grpc.subscribe.GetSubscriberCountResponse;
import kr.magicbox.creator.grpc.subscribe.IsSubscribedRequest;
import kr.magicbox.creator.grpc.subscribe.IsSubscribedResponse;
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
        GetSubscriberCountRequest request = GetSubscriberCountRequest.newBuilder()
                .setCreatorId(creatorId)
                .build();

        ListenableFuture<GetSubscriberCountResponse> future = subscribeServiceFutureStub.getSubscriberCount(request);

        CompletableFuture<Long> result = new CompletableFuture<>();
        future.addListener(() -> {
            try {
                result.complete(future.get().getSubscriberCount());
            } catch (Exception e) {
                result.completeExceptionally(e);
            }
        }, Runnable::run);
        return result;
    }

    @Override
    @CircuitBreaker(name = "subscribeService", fallbackMethod = "isSubscribedFallback")
    @TimeLimiter(name = "subscribeService", fallbackMethod = "isSubscribedFallback")
    public CompletableFuture<Boolean> isSubscribed(Long creatorId, Long userId) {
        IsSubscribedRequest request = IsSubscribedRequest.newBuilder()
                .setCreatorId(creatorId)
                .setUserId(userId)
                .build();

        ListenableFuture<IsSubscribedResponse> future = subscribeServiceFutureStub.isSubscribed(request);

        CompletableFuture<Boolean> result = new CompletableFuture<>();
        future.addListener(() -> {
            try {
                result.complete(future.get().getSubscribed());
            } catch (Exception e) {
                result.completeExceptionally(e);
            }
        }, Runnable::run);
        return result;
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
