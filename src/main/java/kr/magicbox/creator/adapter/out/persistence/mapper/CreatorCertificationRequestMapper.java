package kr.magicbox.creator.adapter.out.persistence.mapper;

import kr.magicbox.creator.adapter.out.persistence.vo.CreatorCertificationRequestVO;
import kr.magicbox.creator.domain.vo.CreatorCertificationRequest;
import org.springframework.stereotype.Component;

@Component
public class CreatorCertificationRequestMapper {

    public CreatorCertificationRequestVO toEntity(CreatorCertificationRequest domain) {
        return CreatorCertificationRequestVO.builder()
                .portfolioUrl(domain.portfolioUrl())
                .genres(domain.genres())
                .requestedAt(domain.requestedAt())
                .build();
    }

    public CreatorCertificationRequest toDomain(CreatorCertificationRequestVO entity) {
        return CreatorCertificationRequest.builder()
                .genres(entity.getGenres())
                .portfolioUrl(entity.getPortfolioUrl())
                .requestedAt(entity.getRequestedAt())
                .build();
    }
}