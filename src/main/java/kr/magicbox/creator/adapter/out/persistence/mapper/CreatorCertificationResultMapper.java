package kr.magicbox.creator.adapter.out.persistence.mapper;

import kr.magicbox.creator.adapter.out.persistence.vo.CreatorCertificationResultVO;
import kr.magicbox.creator.domain.vo.CreatorCertificationResult;
import org.springframework.stereotype.Component;

@Component
public class CreatorCertificationResultMapper {

    public CreatorCertificationResult toDomain(CreatorCertificationResultVO vo) {
        if (vo == null || vo.getReviewMessage() == null) {
            return null;
        }
        return CreatorCertificationResult.builder()
                .reviewMessage(vo.getReviewMessage())
                .reviewedAt(vo.getReviewedAt())
                .build();
    }

    public CreatorCertificationResultVO toEntity(CreatorCertificationResult domain) {
        return CreatorCertificationResultVO.builder()
                .reviewMessage(domain.reviewMessage())
                .reviewedAt(domain.reviewedAt())
                .build();
    }
}