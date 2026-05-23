package kr.magicbox.creator.application.dto.result;

import kr.magicbox.creator.domain.aggregate.CreatorCertification;
import kr.magicbox.creator.domain.enums.MagicGenre;
import kr.magicbox.creator.domain.vo.CreatorCertificationId;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;

@Builder
public record PendingCreatorCertificationResult(
        CreatorCertificationId certificationId,
        UserId userId,
        Set<MagicGenre> genres,
        String portfolioUrl,
        Instant requestedAt
) {

    public static PendingCreatorCertificationResult from(CreatorCertification certification) {
        return PendingCreatorCertificationResult.builder()
                .certificationId(certification.getId())
                .userId(certification.getUserId())
                .genres(certification.getRequest().genres())
                .portfolioUrl(certification.getRequest().portfolioUrl())
                .requestedAt(certification.getRequest().requestedAt())
                .build();
    }
}
