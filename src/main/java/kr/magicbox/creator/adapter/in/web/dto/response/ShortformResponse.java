package kr.magicbox.creator.adapter.in.web.dto.response;

import kr.magicbox.creator.application.dto.result.ShortformResult;
import lombok.Builder;

@Builder
public record ShortformResponse(
        Long shortformId,
        String title,
        String thumbnailUrl,
        String videoUrl,
        long viewCount
) {

    public static ShortformResponse from(ShortformResult result) {
        return ShortformResponse.builder()
                .shortformId(result.shortformId().value())
                .title(result.title())
                .thumbnailUrl(result.thumbnailUrl())
                .videoUrl(result.videoUrl())
                .viewCount(result.viewCount())
                .build();
    }
}