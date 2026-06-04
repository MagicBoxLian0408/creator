package kr.magicbox.creator.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.magicbox.creator.domain.vo.CreatorId;
import lombok.Builder;

import java.time.Instant;

@Builder
public record CreatorProfileUpdatedEvent(
        @JsonProperty("event_id") Long eventId,
        @JsonProperty("creator_id") CreatorId creatorId,
        @JsonProperty("before") ProfileSnapshot before,
        @JsonProperty("after") ProfileSnapshot after,
        @JsonProperty("occurred_at") Instant occurredAt
) implements CreatorDomainEvent {

    public record ProfileSnapshot(
            @JsonProperty("nickname") String nickname,
            @JsonProperty("tagline") String tagline,
            @JsonProperty("profile_image_url") String profileImageUrl
    ) {}

    @Override
    public String key() {
        return creatorId.value().toString();
    }

    @Override
    public CreatorDomainEventType eventType() {
        return CreatorDomainEventType.CREATOR_PROFILE_UPDATED;
    }
}
