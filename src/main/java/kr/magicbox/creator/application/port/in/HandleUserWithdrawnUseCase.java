package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.domain.vo.UserId;

public interface HandleUserWithdrawnUseCase {
    void handleUserWithdrawn(UserId userId);
}
