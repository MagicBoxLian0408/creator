package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.application.dto.query.IsCreatorOwnedByUserQuery;

public interface IsCreatorOwnedByUserUseCase {
    boolean isOwnedByUser(IsCreatorOwnedByUserQuery query);
}