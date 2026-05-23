package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.domain.vo.UserId;

public interface HandleUserBannedUseCase {
    void handleUserBanned(UserId userId);
}
