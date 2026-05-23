package kr.magicbox.creator.domain.vo;

import kr.magicbox.creator.domain.enums.MagicGenre;
import kr.magicbox.creator.domain.exception.InvalidFieldException;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;

public record CreatorCertificationRequest(Set<MagicGenre> genres, String portfolioUrl, Instant requestedAt) {

    @Builder
    public CreatorCertificationRequest {
        if (genres == null || genres.isEmpty()) {
            throw new InvalidFieldException("장르는 필수 값입니다.");
        }
        if (portfolioUrl == null || portfolioUrl.trim().isEmpty()) {
            throw new InvalidFieldException("포트폴리오 URL은 필수 값입니다.");
        }
        if (requestedAt == null) {
            requestedAt = Instant.now();
        }
    }
}