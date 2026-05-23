package kr.magicbox.creator.application.dto.result;

import kr.magicbox.creator.domain.vo.Nickname;
import lombok.Builder;

import java.util.List;

@Builder
public record CreatorMyProfileResult(
        Nickname nickname,
        String tagline,
        long subscriberCount,
        long releaseCount,
        ReviewRating reviewRating,
        List<ReleaseResult> releases,
        List<ShortformResult> shortForms,
        String introduction
) {
}