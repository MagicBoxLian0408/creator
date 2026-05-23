package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.application.dto.result.CreatorMyProfileResult;
import kr.magicbox.creator.application.dto.query.GetMyCreatorProfileQuery;

public interface GetMyCreatorProfileUseCase {

    CreatorMyProfileResult getMyCreatorProfile(GetMyCreatorProfileQuery query);
}
