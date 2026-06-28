package kr.magicbox.creator.adapter.in.web.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatorProfileResponse(
        Long creatorId,
        String nickname,
        String tagline,
        String profileImageUrl,
        long subscriberCount,
        long releaseCount,
        double averageReviewRating,
        List<ReleaseResponse> releases,
        List<ShortformResponse> shortForms,
        String introduction,
        boolean isSubscribed
) {
}
