package kr.magicbox.creator.application.port.out;

import kr.magicbox.creator.application.dto.result.ReleaseResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ReleaseQueryPort {

    CompletableFuture<Long> getReleaseCount(Long creatorId);

    CompletableFuture<List<ReleaseResult>> getReleases(Long creatorId);
}
