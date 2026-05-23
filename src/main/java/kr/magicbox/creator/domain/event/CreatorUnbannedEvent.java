package kr.magicbox.creator.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.magicbox.creator.domain.vo.CreatorId;
import lombok.Builder;

import java.time.Instant;

@Builder
public record CreatorUnbannedEvent(
        @JsonProperty("creator_id") CreatorId creatorId,
        @JsonProperty("occurred_at") Instant occurredAt
) implements CreatorDomainEvent {

    @Override
    public String key() {
        return creatorId.value().toString();
    }

    @Override
    public CreatorDomainEventType eventType() {
        return CreatorDomainEventType.CREATOR_UNBANNED;
    }
}
