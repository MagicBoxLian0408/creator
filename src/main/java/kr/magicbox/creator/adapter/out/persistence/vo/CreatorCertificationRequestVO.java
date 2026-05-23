package kr.magicbox.creator.adapter.out.persistence.vo;

import jakarta.persistence.*;
import kr.magicbox.creator.domain.enums.MagicGenre;
import kr.magicbox.creator.domain.vo.CreatorCertificationRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatorCertificationRequestVO {

    @Column(nullable = false)
    private String portfolioUrl;

    @Column(nullable = false)
    private Instant requestedAt;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "creator_certification_request_genre", joinColumns = @JoinColumn(name = "certification_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private final Set<MagicGenre> genres = new HashSet<>();

    @Builder
    public CreatorCertificationRequestVO(String portfolioUrl, Set<MagicGenre> genres, Instant requestedAt) {
        this.portfolioUrl = portfolioUrl;
        this.requestedAt = (requestedAt != null) ? requestedAt : Instant.now();
        this.genres.addAll(genres);
    }

    public static CreatorCertificationRequestVO of(CreatorCertificationRequest creatorCertificationRequest) {
        return CreatorCertificationRequestVO.builder()
                .requestedAt(creatorCertificationRequest.requestedAt())
                .genres(creatorCertificationRequest.genres())
                .portfolioUrl(creatorCertificationRequest.portfolioUrl())
                .build();
    }
}