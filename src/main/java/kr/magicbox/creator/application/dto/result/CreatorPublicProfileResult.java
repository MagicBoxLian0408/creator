package kr.magicbox.creator.application.dto.result;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatorPublicProfileResult(
        String nickname,
        String tagline,
        long subscriberCount,
        long releaseCount,
        ReviewRating reviewRating,
        List<ReleaseResult> releases,
        List<ShortformResult> shortForms,
        String introduction,
        boolean isSubscribed
) {
}