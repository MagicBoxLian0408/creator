package kr.magicbox.creator.adapter.in.web.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatorMyProfileResponse(
        Long creatorId,
        String nickname,
        String tagline,
        long subscriberCount,
        long releaseCount,
        double averageReviewRating,
        List<ReleaseResponse> releases,
        List<ShortformResponse> shortForms,
        String introduction
) {
}
