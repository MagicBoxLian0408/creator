package kr.magicbox.creator.adapter.out.persistence.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kr.magicbox.creator.domain.vo.CreatorCertificationResult;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatorCertificationResultVO {

    @Column(columnDefinition = "TEXT")
    private String reviewMessage;

    @Column
    private Instant reviewedAt;

    @Builder
    public CreatorCertificationResultVO(String reviewMessage, Instant reviewedAt) {
        this.reviewMessage = reviewMessage;
        this.reviewedAt = (reviewedAt != null) ? reviewedAt : Instant.now();
    }

    public static CreatorCertificationResultVO of(CreatorCertificationResult result) {
        return CreatorCertificationResultVO.builder()
                .reviewMessage(result.reviewMessage())
                .reviewedAt(result.reviewedAt())
                .build();
    }
}