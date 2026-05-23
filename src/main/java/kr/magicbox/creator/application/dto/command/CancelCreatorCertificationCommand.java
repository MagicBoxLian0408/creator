package kr.magicbox.creator.application.dto.command;

import kr.magicbox.creator.domain.vo.CreatorCertificationId;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.Builder;

@Builder
public record CancelCreatorCertificationCommand(
        CreatorCertificationId creatorCertificationId,
        UserId userId
) {
    public static CancelCreatorCertificationCommand of(CreatorCertificationId creatorCertificationId, UserId userId) {
        return new CancelCreatorCertificationCommand(creatorCertificationId, userId);
    }
}
