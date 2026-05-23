package kr.magicbox.creator.application.dto.result;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ReleaseResult(
        ReleaseId releaseId,
        String title,
        String thumbnailUrl,
        ReleaseLevel level,
        String creatorNickname,
        long price,
        int limitedQuantity,
        int soldQuantity,
        ReleaseStatus status,
        Instant scheduledAt,
        Instant createdAt
) {
}