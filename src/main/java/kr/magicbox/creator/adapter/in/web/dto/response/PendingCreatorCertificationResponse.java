package kr.magicbox.creator.adapter.in.web.dto.response;

import kr.magicbox.creator.application.dto.result.PendingCreatorCertificationResult;
import kr.magicbox.creator.domain.enums.MagicGenre;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;

@Builder
public record PendingCreatorCertificationResponse(
        Long certificationId,
        Long userId,
        Set<MagicGenre> genres,
        String portfolioUrl,
        Instant requestedAt
) {

    public static PendingCreatorCertificationResponse from(PendingCreatorCertificationResult result) {
        return PendingCreatorCertificationResponse.builder()
                .certificationId(result.certificationId().value())
                .userId(result.userId().value())
                .genres(result.genres())
                .portfolioUrl(result.portfolioUrl())
                .requestedAt(result.requestedAt())
                .build();
    }
}