package kr.magicbox.creator.application.dto.result;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatorPublicProfileResult(
        Long creatorId,
        String nickname,
        String tagline,
        String profileImageUrl,
        long subscriberCount,
        long releaseCount,
        double averageReviewRating,
        List<ReleaseResult> releases,
        List<ShortformResult> shortForms,
        String introduction,
        boolean isSubscribed
) {
}