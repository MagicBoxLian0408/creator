package kr.magicbox.creator.application.port.out;

import kr.magicbox.creator.application.dto.result.ReleaseResult;

import java.util.List;

public interface ReleaseQueryPort {

    long getReleaseCount(Long creatorId);

    List<ReleaseResult> getReleases(Long creatorId);
}
