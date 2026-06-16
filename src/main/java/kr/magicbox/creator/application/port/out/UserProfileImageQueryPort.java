package kr.magicbox.creator.application.port.out;

import kr.magicbox.creator.domain.vo.UserId;

public interface UserProfileImageQueryPort {
    String getProfileImageUrl(UserId userId);
}
