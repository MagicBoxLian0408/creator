package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.domain.vo.CreatorId;

public interface GetCreatorNicknameByCreatorIdUseCase {
    String getCreatorNickname(CreatorId creatorId);
}
