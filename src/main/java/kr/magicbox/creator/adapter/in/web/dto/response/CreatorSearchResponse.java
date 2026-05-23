package kr.magicbox.creator.adapter.in.web.dto.response;

import lombok.Builder;

@Builder
public record CreatorSearchResponse(
        Long creatorId,
        String nickname,
        String introduction,
        String profileImageUrl,
        String tagline
) {
}
