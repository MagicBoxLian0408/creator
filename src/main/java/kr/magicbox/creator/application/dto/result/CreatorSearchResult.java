package kr.magicbox.creator.application.dto.result;

import kr.magicbox.creator.domain.aggregate.Creator;
import kr.magicbox.creator.domain.vo.CreatorId;
import kr.magicbox.creator.domain.vo.Nickname;
import lombok.Builder;

@Builder
public record CreatorSearchResult(
        CreatorId creatorId,
        Nickname nickname,
        String introduction,
        String profileImageUrl,
        String tagline,
        long subscriberCount
) {

    public static CreatorSearchResult from(Creator creator, long subscriberCount) {
        return CreatorSearchResult.builder()
                .creatorId(creator.getId())
                .nickname(creator.getNickname())
                .introduction(creator.getIntroduction())
                .profileImageUrl(creator.getProfileImageUrl())
                .tagline(creator.getTagline())
                .subscriberCount(subscriberCount)
                .build();
    }
}
