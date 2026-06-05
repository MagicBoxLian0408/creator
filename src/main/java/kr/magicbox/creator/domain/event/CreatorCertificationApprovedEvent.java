package kr.magicbox.creator.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.magicbox.creator.domain.enums.CreatorStatus;
import kr.magicbox.creator.domain.enums.MagicGenre;
import kr.magicbox.creator.domain.vo.CreatorCertificationId;
import kr.magicbox.creator.domain.vo.CreatorId;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;

@Builder
public record CreatorCertificationApprovedEvent(
        @JsonProperty("user_id") UserId userId,
        @JsonProperty("creator_id") CreatorId creatorId,
        @JsonProperty("certification_id") CreatorCertificationId certificationId,
        @JsonProperty("nickname") String nickname,
        @JsonProperty("genres") Set<MagicGenre> genres,
        @JsonProperty("status") CreatorStatus status,
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
