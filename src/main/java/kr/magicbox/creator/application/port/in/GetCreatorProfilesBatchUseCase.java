package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.domain.vo.CreatorId;

import java.util.List;
import java.util.Map;

public interface GetCreatorProfilesBatchUseCase {
    Map<Long, GetCreatorProfileByCreatorIdUseCase.CreatorProfile> getCreatorProfilesBatch(List<CreatorId> creatorIds);
}
