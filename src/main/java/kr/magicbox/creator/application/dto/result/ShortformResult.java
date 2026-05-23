package kr.magicbox.creator.application.dto.result;

import lombok.Builder;

@Builder
public record ShortformResult(
        ShortformId shortformId,
        String title,
        String thumbnailUrl,
        String videoUrl,
        long viewCount
) {
}