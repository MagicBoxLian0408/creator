package kr.magicbox.creator.application.dto.command;

import kr.magicbox.creator.domain.vo.CreatorCertificationId;
import kr.magicbox.creator.domain.vo.UserId;

public record ReviewCreatorCertificationCommand(
        UserId reviewerId,
        CreatorCertificationId certificationId,
        ReviewDecisionCommand certificationStatus,
        String reviewMessage
) {
}
