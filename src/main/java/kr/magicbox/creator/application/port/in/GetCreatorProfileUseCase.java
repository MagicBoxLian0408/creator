package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.application.dto.result.CreatorPublicProfileResult;
import kr.magicbox.creator.application.dto.query.GetCreatorProfileQuery;

public interface GetCreatorProfileUseCase {

    CreatorPublicProfileResult getCreatorProfile(GetCreatorProfileQuery query);
}
