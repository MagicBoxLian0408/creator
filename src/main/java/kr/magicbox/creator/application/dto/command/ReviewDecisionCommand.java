package kr.magicbox.creator.application.dto.command;

import kr.magicbox.creator.domain.enums.CreatorCertificationStatus;

public enum ReviewDecisionCommand {
    APPROVED, REJECTED;

    public CreatorCertificationStatus toStatus() {
        return CreatorCertificationStatus.valueOf(this.name());
    }
}