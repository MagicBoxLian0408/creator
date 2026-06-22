package kr.magicbox.creator.adapter.out.communication.grpc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import kr.magicbox.creator.adapter.out.communication.grpc.exception.ReleaseServiceUnavailableException;
import kr.magicbox.creator.application.dto.result.ReleaseId;
import kr.magicbox.creator.application.dto.result.ReleaseLevel;
import kr.magicbox.creator.application.dto.result.ReleaseResult;
import kr.magicbox.creator.application.port.out.ReleaseQueryPort;
import kr.magicbox.creator.grpc.release.GetReleaseCountRequest;
import kr.magicbox.creator.grpc.release.GetReleasesByCreatorIdRequest;
import kr.magicbox.creator.grpc.release.ReleaseServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReleaseQueryGrpcAdapter implements ReleaseQueryPort {

    private final ReleaseServiceGrpc.ReleaseServiceFutureStub releaseServiceFutureStub;

    @Override
    @CircuitBreaker(name = "releaseService", fallbackMethod = "getReleaseCountFallback")
    @TimeLimiter(name = "releaseService", fallbackMethod = "getReleaseCountFallback")
    public CompletableFuture<Long> getReleaseCount(Long creatorId) {
        return GrpcFutures.toCompletable(
                releaseServiceFutureStub.getReleaseCount(
                        GetReleaseCountRequest.newBuilder().setCreatorId(creatorId).build()
                )
        ).thenApply(response -> response.getReleaseCount());
    }

    @Override
    @CircuitBreaker(name = "releaseService", fallbackMethod = "getReleasesFallback")
    @TimeLimiter(name = "releaseService", fallbackMethod = "getReleasesFallback")
    public CompletableFuture<List<ReleaseResult>> getReleases(Long creatorId) {
        return GrpcFutures.toCompletable(
                releaseServiceFutureStub.getReleasesByCreatorId(
                        GetReleasesByCreatorIdRequest.newBuilder().setCreatorId(creatorId).build()
                )
        ).thenApply(response -> response.getReleasesList().stream()
                .map(release -> ReleaseResult.builder()
                        .releaseId(ReleaseId.of(release.getReleaseId()))
                        .title(release.getTitle())
                        .thumbnailUrl(release.getThumbnailUrl())
                        .level(ReleaseLevel.valueOf(release.getLevel().name()))
                        .creatorNickname(release.getCreatorNickname())
                        .price(release.getPrice())
                        .limitedQuantity(release.getLimitedQuantity())
                        .build())
                .toList());
    }

    @SuppressWarnings("unused")
    private CompletableFuture<Long> getReleaseCountFallback(Long creatorId, Throwable throwable) {
        log.warn("릴리즈 개수 조회 서비스 연결 실패");
        throw new ReleaseServiceUnavailableException(throwable);
    }

    @SuppressWarnings("unused")
    private CompletableFuture<List<ReleaseResult>> getReleasesFallback(Long creatorId, Throwable throwable) {
        log.warn("릴리즈 목록 조회 서비스 연결 실패");
        throw new ReleaseServiceUnavailableException(throwable);
    }
}
