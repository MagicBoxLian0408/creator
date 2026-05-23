package kr.magicbox.creator.adapter.out.persistence.entity;

import jakarta.persistence.*;
import kr.magicbox.creator.adapter.out.persistence.vo.CreatorCertificationRequestVO;
import kr.magicbox.creator.adapter.out.persistence.vo.CreatorCertificationResultVO;
import kr.magicbox.creator.domain.aggregate.CreatorCertification;
import kr.magicbox.creator.domain.enums.CreatorCertificationStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Optional;

@Entity
@Table(name = "creator_certification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreatorCertificationEntity extends BaseEntity {

    @Version
    private Long version;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CreatorCertificationStatus status;

    @Embedded
    private CreatorCertificationRequestVO request;

    @Embedded
    private CreatorCertificationResultVO result;

    @Builder
    public CreatorCertificationEntity(Long userId, CreatorCertificationRequestVO request) {
        this.userId = userId;
        this.status = CreatorCertificationStatus.PENDING;
        this.request = request;
    }

    public void updateFromDomain(CreatorCertification creatorCertification) {
        this.status = creatorCertification.getStatus();
        this.request = CreatorCertificationRequestVO.of(creatorCertification.getRequest());
        this.result = Optional.ofNullable(creatorCertification.getResult())
                .map(CreatorCertificationResultVO::of)
                .orElse(null);
    }
}