package kr.magicbox.creator.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.magicbox.creator.domain.enums.MagicGenre;
import kr.magicbox.creator.domain.vo.CreatorId;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;

@Builder
public record CreatorProfileUpdatedEvent(
        @JsonProperty("creator_id") CreatorId creatorId,
        @JsonProperty("before") ProfileSnapshot before,
        @JsonProperty("after") ProfileSnapshot after,
        @JsonProperty("occurred_at") Instant occurredAt
) implements CreatorDomainEvent {

    public record ProfileSnapshot(
            @JsonProperty("nickname") String nickname,
            @JsonProperty("tagline") String tagline,
            @JsonProperty("profile_image_url") String profileImageUrl,
            @JsonProperty("introduction") String introduction,
            @JsonProperty("genres") Set<MagicGenre> genres
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
