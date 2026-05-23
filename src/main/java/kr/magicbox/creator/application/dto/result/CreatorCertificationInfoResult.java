package kr.magicbox.creator.application.dto.result;

import jakarta.annotation.Nullable;
import kr.magicbox.creator.domain.enums.CreatorCertificationStatus;
import kr.magicbox.creator.domain.enums.MagicGenre;
import kr.magicbox.creator.domain.vo.CreatorCertificationId;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;

@Builder
public record CreatorCertificationInfoResult(
        CreatorCertificationId certificationId,
        Set<MagicGenre> genres,
        String portfolioUrl,
        Instant requestedAt,
        CreatorCertificationStatus status,
        @Nullable String reviewMessage,
        @Nullable Instant reviewedAt
) {
}
