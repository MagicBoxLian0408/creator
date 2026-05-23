package kr.magicbox.creator.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.magicbox.creator.domain.vo.CreatorId;
import lombok.Builder;

import java.time.Instant;

@Builder
public record CreatorRevokedEvent(
        CreatorId creatorId,
        Instant occurredAt
) implements CreatorDomainEvent {

    @Override
    public String key() {
        return creatorId.value().toString();
    }

    @Override
    public CreatorDomainEventType eventType() {
        return CreatorDomainEventType.CREATOR_REVOKED;
    }
}