package kr.magicbox.creator.adapter.in.web.dto.response;

import kr.magicbox.creator.application.dto.result.ReviewRating;
import lombok.Builder;

import java.util.List;

@Builder
public record CreatorProfileResponse(
        Long creatorId,
        String nickname,
        String tagline,
        long subscriberCount,
        long releaseCount,
        ReviewRating reviewRating,
        List<ReleaseResponse> releases,
        List<ShortformResponse> shortForms,
        String introduction,
        boolean isSubscribed
) {
}
