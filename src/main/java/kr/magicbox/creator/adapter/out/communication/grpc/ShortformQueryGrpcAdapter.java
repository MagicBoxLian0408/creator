package kr.magicbox.creator.adapter.out.communication.grpc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import kr.magicbox.creator.adapter.out.communication.grpc.exception.ShortformServiceUnavailableException;
import kr.magicbox.creator.application.dto.result.ShortformId;
import kr.magicbox.creator.application.dto.result.ShortformResult;
import kr.magicbox.creator.application.port.out.ShortformQueryPort;
import kr.magicbox.creator.grpc.shortform.GetShortformsByCreatorIdRequest;
import kr.magicbox.creator.grpc.shortform.ShortformServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShortformQueryGrpcAdapter implements ShortformQueryPort {

    private final ShortformServiceGrpc.ShortformServiceFutureStub shortformServiceFutureStub;

    @Override
    @CircuitBreaker(name = "shortformService", fallbackMethod = "getShortformsFallback")
    @TimeLimiter(name = "shortformService", fallbackMethod = "getShortformsFallback")
    public CompletableFuture<List<ShortformResult>> getShortforms(Long creatorId) {
        return GrpcFutures.toCompletable(
                shortformServiceFutureStub.getShortformsByCreatorId(
                        GetShortformsByCreatorIdRequest.newBuilder().setCreatorId(creatorId).build()
                )
        ).thenApply(response -> response.getShortformsList().stream()
                .map(shortform -> ShortformResult.builder()
                        .shortformId(ShortformId.of(shortform.getShortformId()))
                        .title(shortform.getTitle())
                        .thumbnailUrl(shortform.getThumbnailUrl())
                        .videoUrl(shortform.getVideoUrl())
                        .viewCount(shortform.getViewCount())
                        .build())
                .toList());
    }

    @SuppressWarnings("unused")
    private CompletableFuture<List<ShortformResult>> getShortformsFallback(Long creatorId, Throwable throwable) {
        log.warn("숏폼 서비스 연결 실패");
        throw new ShortformServiceUnavailableException(throwable);
    }
}
