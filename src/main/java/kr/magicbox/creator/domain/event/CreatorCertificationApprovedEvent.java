package kr.magicbox.creator.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.magicbox.creator.domain.vo.CreatorCertificationId;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.Builder;

import java.time.Instant;

@Builder
public record CreatorCertificationApprovedEvent(
        @JsonProperty("user_id") UserId userId,
        @JsonProperty("certification_id") CreatorCertificationId certificationId,
        @JsonProperty("occurred_at") Instant occurredAt
) implements CreatorDomainEvent {

    @Override
    public String key() {
        return userId.value().toString();
    }

    @Override
    public CreatorDomainEventType eventType() {
        return CreatorDomainEventType.CREATOR_CERTIFICATION_APPROVED;
    }
}