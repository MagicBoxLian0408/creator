package kr.magicbox.creator.application.port.out;

import kr.magicbox.creator.application.dto.result.ShortformResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ShortformQueryPort {

    CompletableFuture<List<ShortformResult>> getShortforms(Long creatorId);
}
