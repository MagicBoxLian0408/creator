package kr.magicbox.creator.application.dto.query;

import kr.magicbox.creator.domain.vo.CreatorId;
import kr.magicbox.creator.domain.vo.UserId;

public record IsCreatorOwnedByUserQuery(CreatorId creatorId, UserId userId) {
    public static IsCreatorOwnedByUserQuery of(CreatorId creatorId, UserId userId) {
        return new IsCreatorOwnedByUserQuery(creatorId, userId);
    }
}