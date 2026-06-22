package kr.magicbox.creator.adapter.out.communication.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import kr.magicbox.creator.adapter.out.communication.grpc.exception.ReleaseServiceUnavailableException;
import kr.magicbox.creator.application.dto.result.ReleaseId;
import kr.magicbox.creator.application.dto.result.ReleaseLevel;
import kr.magicbox.creator.application.dto.result.ReleaseResult;
import kr.magicbox.creator.application.port.out.ReleaseQueryPort;
import kr.magicbox.creator.grpc.release.GetReleaseCountRequest;
import kr.magicbox.creator.grpc.release.GetReleaseCountResponse;
import kr.magicbox.creator.grpc.release.GetReleasesByCreatorIdRequest;
import kr.magicbox.creator.grpc.release.GetReleasesByCreatorIdResponse;
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
        GetReleaseCountRequest request = GetReleaseCountRequest.newBuilder()
                .setCreatorId(creatorId)
                .build();

        ListenableFuture<GetReleaseCountResponse> future = releaseServiceFutureStub.getReleaseCount(request);

        CompletableFuture<Long> result = new CompletableFuture<>();
        future.addListener(() -> {
            try {
                result.complete(future.get().getReleaseCount());
            } catch (Exception e) {
                result.completeExceptionally(e);
            }
        }, Runnable::run);
        return result;
    }

    @Override
    @CircuitBreaker(name = "releaseService", fallbackMethod = "getReleasesFallback")
    @TimeLimiter(name = "releaseService", fallbackMethod = "getReleasesFallback")
    public CompletableFuture<List<ReleaseResult>> getReleases(Long creatorId) {
        GetReleasesByCreatorIdRequest request = GetReleasesByCreatorIdRequest.newBuilder()
                .setCreatorId(creatorId)
                .build();

        ListenableFuture<GetReleasesByCreatorIdResponse> future = releaseServiceFutureStub.getReleasesByCreatorId(request);

        CompletableFuture<List<ReleaseResult>> result = new CompletableFuture<>();
        future.addListener(() -> {
            try {
                List<ReleaseResult> releases = future.get().getReleasesList().stream()
                        .map(release -> ReleaseResult.builder()
                                .releaseId(ReleaseId.of(release.getReleaseId()))
                                .title(release.getTitle())
                                .thumbnailUrl(release.getThumbnailUrl())
                                .level(ReleaseLevel.valueOf(release.getLevel().name()))
                                .creatorNickname(release.getCreatorNickname())
                                .price(release.getPrice())
                                .limitedQuantity(release.getLimitedQuantity())
                                .build())
                        .toList();
                result.complete(releases);
            } catch (Exception e) {
                result.completeExceptionally(e);
            }
        }, Runnable::run);
        return result;
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
