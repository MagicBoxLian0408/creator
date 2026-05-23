package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.application.dto.query.GetAllPendingCreatorCertificationsQuery;
import kr.magicbox.creator.application.dto.result.PendingCreatorCertificationResult;

import java.util.List;

public interface GetAllPendingCreatorCertificationsUseCase {

    List<PendingCreatorCertificationResult> getAllPendingCreatorCertifications(GetAllPendingCreatorCertificationsQuery query);
}
