package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.domain.vo.UserId;

public interface HandleUserProfileUpdatedUseCase {
    void handleUserProfileUpdated(UserId userId, String profileImageUrl);
}
