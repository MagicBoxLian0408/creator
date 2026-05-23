package kr.magicbox.creator.adapter.in.web.dto.response;

import kr.magicbox.creator.application.dto.result.CreatorCertificationInfoResult;
import kr.magicbox.creator.domain.enums.CreatorCertificationStatus;
import kr.magicbox.creator.domain.enums.MagicGenre;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;

@Builder
public record CreatorCertificationInfoResponse(
        Long certificationId,
        Set<MagicGenre> genres,
        String portfolioUrl,
        Instant requestedAt,
        CreatorCertificationStatus status,
        String reviewMessage,
        Instant reviewedAt
) {

    public static CreatorCertificationInfoResponse from(CreatorCertificationInfoResult result) {
        return CreatorCertificationInfoResponse.builder()
                .certificationId(result.certificationId().value())
                .genres(result.genres())
                .portfolioUrl(result.portfolioUrl())
                .requestedAt(result.requestedAt())
                .status(result.status())
                .reviewMessage(result.reviewMessage())
                .reviewedAt(result.reviewedAt())
                .build();
    }
}