package kr.magicbox.creator.application.service.certification;

import kr.magicbox.creator.application.dto.result.CreatorCertificationInfoResult;
import kr.magicbox.creator.application.dto.query.GetMyCreatorCertificationsQuery;
import kr.magicbox.creator.application.port.in.GetMyCreatorCertificationsUseCase;
import kr.magicbox.creator.application.port.out.CreatorCertificationRepositoryPort;
import kr.magicbox.creator.domain.aggregate.CreatorCertification;
import kr.magicbox.creator.domain.vo.CreatorCertificationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMyCreatorCertificationsService implements GetMyCreatorCertificationsUseCase {

    private final CreatorCertificationRepositoryPort creatorCertificationRepositoryPort;

    @Transactional(readOnly = true)
    @Override
    public List<CreatorCertificationInfoResult> getMyCreatorCertifications(GetMyCreatorCertificationsQuery query) {
        return creatorCertificationRepositoryPort.findAllByUserIdByCursor(query.userId(), query.cursorId(), query.size())
                .stream()
                .map(this::toResult)
                .toList();
    }

    private CreatorCertificationInfoResult toResult(CreatorCertification creatorCertification) {
        CreatorCertificationResult result = creatorCertification.getResult();
        return CreatorCertificationInfoResult.builder()
                .certificationId(creatorCertification.getId())
                .genres(creatorCertification.getRequest().genres())
                .portfolioUrl(creatorCertification.getRequest().portfolioUrl())
                .requestedAt(creatorCertification.getRequest().requestedAt())
                .status(creatorCertification.getStatus())
                .reviewMessage(result != null ? result.reviewMessage() : null)
                .reviewedAt(result != null ? result.reviewedAt() : null)
                .build();
    }
}
