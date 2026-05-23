package kr.magicbox.creator.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CreatorDomainEventType {

    CREATOR_CERTIFICATION_APPROVED("creator-certification-approved"),
    CREATOR_CERTIFICATION_REJECTED("creator-certification-rejected"),
    CREATOR_REVOKED("creator-revoked"),
    CREATOR_UNBANNED("creator-unbanned");

    private final String value;
}