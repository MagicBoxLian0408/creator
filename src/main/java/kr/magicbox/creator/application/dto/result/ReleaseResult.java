package kr.magicbox.creator.application.dto.result;

import lombok.Builder;

@Builder
public record ReleaseResult(
        ReleaseId releaseId,
        String title,
        String thumbnailUrl,
        ReleaseLevel level,
        String creatorNickname,
        long price,
        int limitedQuantity
) {
}