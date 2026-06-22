package kr.magicbox.creator.application.service;

import kr.magicbox.creator.application.dto.result.CreatorPublicProfileResult;
import kr.magicbox.creator.application.dto.query.GetCreatorProfileQuery;
import kr.magicbox.creator.application.port.in.GetCreatorProfileUseCase;
import kr.magicbox.creator.application.port.out.*;
import kr.magicbox.creator.domain.aggregate.Creator;
import kr.magicbox.creator.domain.exception.CreatorNotFoundException;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetCreatorProfileService implements GetCreatorProfileUseCase {

    private final CreatorRepositoryPort creatorRepositoryPort;
    private final SubscribeQueryPort subscribeQueryPort;
    private final ReleaseQueryPort releaseQueryPort;
    private final ShortformQueryPort shortformQueryPort;
    private final ReviewRatingQueryPort reviewRatingQueryPort;

    @Override
    @Transactional(readOnly = true)
    public CreatorPublicProfileResult getCreatorProfile(GetCreatorProfileQuery query) {
        UserId userId = query.userId();
        Creator creator = creatorRepositoryPort.findByNickname(query.nickname())
                .orElseThrow(CreatorNotFoundException::new);

        Long creatorId = creator.getId().value();

        return CreatorPublicProfileResult.builder()
                .creatorId(creatorId)
                .nickname(creator.getNicknameValue())
                .tagline(creator.getTagline())
                .profileImageUrl(creator.getProfileImageUrl())
                .subscriberCount(subscribeQueryPort.getSubscriberCount(creatorId).join())
                .releaseCount(releaseQueryPort.getReleaseCount(creatorId).join())
                .averageReviewRating(reviewRatingQueryPort.getReviewRating(creatorId).join().value())
                .releases(releaseQueryPort.getReleases(creatorId).join())
                .shortForms(shortformQueryPort.getShortforms(creatorId).join())
                .introduction(creator.getIntroduction())
                .isSubscribed(userId != null && subscribeQueryPort.isSubscribed(creatorId, userId.value()).join())
                .build();
    }
}
