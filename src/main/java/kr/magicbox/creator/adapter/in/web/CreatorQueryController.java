package kr.magicbox.creator.adapter.in.web;

import kr.magicbox.creator.adapter.in.web.constants.CursorConstants;
import kr.magicbox.creator.adapter.in.web.dto.response.CreatorMyProfileResponse;
import kr.magicbox.creator.adapter.in.web.dto.response.CreatorProfileResponse;
import kr.magicbox.creator.adapter.in.web.dto.response.CreatorSearchResponse;
import kr.magicbox.creator.adapter.in.web.dto.response.CursorResponse;
import kr.magicbox.creator.adapter.in.web.dto.response.ReleaseResponse;
import kr.magicbox.creator.adapter.in.web.dto.response.ShortformResponse;
import kr.magicbox.creator.adapter.in.web.validation.CursorSize;
import kr.magicbox.creator.application.dto.result.CreatorMyProfileResult;
import kr.magicbox.creator.application.dto.result.CreatorPublicProfileResult;
import kr.magicbox.creator.application.dto.query.GetAllCreatorsQuery;
import kr.magicbox.creator.application.dto.query.GetCreatorProfileQuery;
import kr.magicbox.creator.application.dto.query.GetMyCreatorProfileQuery;
import kr.magicbox.creator.application.port.in.GetAllCreatorsUseCase;
import kr.magicbox.creator.application.port.in.GetCreatorProfileUseCase;
import kr.magicbox.creator.application.port.in.GetMyCreatorProfileUseCase;
import kr.magicbox.creator.domain.vo.Nickname;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class CreatorQueryController {

    private final GetCreatorProfileUseCase getCreatorProfileUseCase;
    private final GetMyCreatorProfileUseCase getMyCreatorProfileUseCase;
    private final GetAllCreatorsUseCase getAllCreatorsUseCase;

    @GetMapping("/profile/{nickname}")
    public ResponseEntity<CreatorProfileResponse> getProfile(
            @AuthenticationPrincipal UserId userId,
            @PathVariable String nickname
    ) {
        CreatorPublicProfileResult result = getCreatorProfileUseCase.getCreatorProfile(
                GetCreatorProfileQuery.of(Nickname.of(nickname), userId)
        );
        return ResponseEntity.ok(CreatorProfileResponse.builder()
                .creatorId(result.creatorId())
                .nickname(result.nickname())
                .tagline(result.tagline())
                .profileImageUrl(result.profileImageUrl())
                .subscriberCount(result.subscriberCount())
                .releaseCount(result.releaseCount())
                .reviewRating(result.reviewRating())
                .releases(result.releases().stream().map(ReleaseResponse::from).toList())
                .shortForms(result.shortForms().stream().map(ShortformResponse::from).toList())
                .introduction(result.introduction())
                .isSubscribed(result.isSubscribed())
                .build());
    }

    @GetMapping("/profile/me")
    public ResponseEntity<CreatorMyProfileResponse> getMyProfile(
            @AuthenticationPrincipal UserId userId
    ) {
        CreatorMyProfileResult result = getMyCreatorProfileUseCase.getMyCreatorProfile(GetMyCreatorProfileQuery.of(userId));
        return ResponseEntity.ok(CreatorMyProfileResponse.builder()
                .creatorId(result.creatorId())
                .nickname(result.nickname().value())
                .tagline(result.tagline())
                .subscriberCount(result.subscriberCount())
                .releaseCount(result.releaseCount())
                .reviewRating(result.reviewRating())
                .releases(result.releases().stream().map(ReleaseResponse::from).toList())
                .shortForms(result.shortForms().stream().map(ShortformResponse::from).toList())
                .introduction(result.introduction())
                .build());
    }

    @GetMapping
    public ResponseEntity<CursorResponse<CreatorSearchResponse>> getAllCreators(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = CursorConstants.DEFAULT_SIZE) @CursorSize Integer size) {
        List<CreatorSearchResponse> contents = getAllCreatorsUseCase.getAllCreators(GetAllCreatorsQuery.of(cursor, size + 1))
                .stream()
                .map(content ->
                        CreatorSearchResponse.builder()
                                .creatorId(content.creatorId().value())
                                .nickname(content.nickname().value())
                                .introduction(content.introduction())
                                .profileImageUrl(content.profileImageUrl())
                                .tagline(content.tagline())
                                .build()
                )
                .toList();
        return ResponseEntity.ok(CursorResponse.of(contents, size, CreatorSearchResponse::creatorId));
    }

}
