package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.domain.vo.CreatorId;

public interface GetCreatorProfileByCreatorIdUseCase {
    CreatorProfile getCreatorProfile(CreatorId creatorId);

    record CreatorProfile(Long creatorId, String nickname, String profileImageUrl) {}
}
