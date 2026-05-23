package kr.magicbox.creator.application.service.certification;

import kr.magicbox.creator.application.dto.query.GetAllPendingCreatorCertificationsQuery;
import kr.magicbox.creator.application.dto.result.PendingCreatorCertificationResult;
import kr.magicbox.creator.application.port.in.GetAllPendingCreatorCertificationsUseCase;
import kr.magicbox.creator.application.port.out.CreatorCertificationRepositoryPort;
import kr.magicbox.creator.domain.enums.CreatorCertificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllPendingCreatorCertificationsService implements GetAllPendingCreatorCertificationsUseCase {

    private final CreatorCertificationRepositoryPort creatorCertificationRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public List<PendingCreatorCertificationResult> getAllPendingCreatorCertifications(GetAllPendingCreatorCertificationsQuery query) {
        return creatorCertificationRepositoryPort.findAllByStatusByCursor(CreatorCertificationStatus.PENDING, query.cursorId(), query.size())
                .stream()
                .map(PendingCreatorCertificationResult::from)
                .toList();
    }
}
