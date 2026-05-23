package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.domain.vo.CreatorId;
import kr.magicbox.creator.domain.vo.UserId;

public interface GetCreatorIdByUserIdUseCase {
    CreatorId getCreatorIdByUserId(UserId userId);
}
