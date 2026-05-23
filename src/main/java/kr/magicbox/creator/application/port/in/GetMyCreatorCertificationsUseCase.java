package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.application.dto.result.CreatorCertificationInfoResult;
import kr.magicbox.creator.application.dto.query.GetMyCreatorCertificationsQuery;

import java.util.List;

public interface GetMyCreatorCertificationsUseCase {

    List<CreatorCertificationInfoResult> getMyCreatorCertifications(GetMyCreatorCertificationsQuery query);
}
