package kr.magicbox.creator.application.service;

import kr.magicbox.creator.application.dto.result.CreatorMyProfileResult;
import kr.magicbox.creator.application.dto.query.GetMyCreatorProfileQuery;
import kr.magicbox.creator.application.port.in.GetMyCreatorProfileUseCase;
import kr.magicbox.creator.application.port.out.*;
import kr.magicbox.creator.domain.aggregate.Creator;
import kr.magicbox.creator.domain.exception.CreatorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetMyCreatorProfileService implements GetMyCreatorProfileUseCase {

    private final CreatorRepositoryPort creatorRepositoryPort;
    private final SubscribeQueryPort subscribeQueryPort;
    private final ReleaseQueryPort releaseQueryPort;
    private final ShortformQueryPort shortformQueryPort;
    private final ReviewRatingQueryPort reviewRatingQueryPort;

    @Override
    @Transactional(readOnly = true)
    public CreatorMyProfileResult getMyCreatorProfile(GetMyCreatorProfileQuery query) {
        Creator creator = creatorRepositoryPort.findByUserId(query.userId())
                .orElseThrow(CreatorNotFoundException::new);

        Long creatorId = creator.getId().value();

        return CreatorMyProfileResult.builder()
                .creatorId(creatorId)
                .nickname(creator.getNickname())
                .tagline(creator.getTagline())
                .subscriberCount(subscribeQueryPort.getSubscriberCount(creatorId).join())
                .releaseCount(releaseQueryPort.getReleaseCount(creatorId).join())
                .averageReviewRating(reviewRatingQueryPort.getReviewRating(creatorId).join().value())
                .releases(releaseQueryPort.getReleases(creatorId).join())
                .shortForms(shortformQueryPort.getShortforms(creatorId).join())
                .introduction(creator.getIntroduction())
                .build();
    }
}
