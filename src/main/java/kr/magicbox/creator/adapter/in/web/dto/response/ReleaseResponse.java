package kr.magicbox.creator.adapter.in.web.dto.response;

import kr.magicbox.creator.application.dto.result.ReleaseLevel;
import kr.magicbox.creator.application.dto.result.ReleaseResult;
import lombok.Builder;

import java.util.List;

@Builder
public record ReleaseResponse(
        Long releaseId,
        String title,
        List<String> mediaUrls,
        ReleaseLevel level,
        String creatorNickname,
        long price,
        int limitedQuantity
) {

    public static ReleaseResponse from(ReleaseResult result) {
        return ReleaseResponse.builder()
                .releaseId(result.releaseId().value())
                .title(result.title())
                .mediaUrls(result.mediaUrls())
                .level(result.level())
                .creatorNickname(result.creatorNickname())
                .price(result.price())
                .limitedQuantity(result.limitedQuantity())
                .build();
    }
}
