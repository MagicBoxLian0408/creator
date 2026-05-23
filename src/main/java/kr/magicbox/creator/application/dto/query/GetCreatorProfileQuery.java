package kr.magicbox.creator.application.dto.query;

import jakarta.annotation.Nullable;
import kr.magicbox.creator.domain.vo.Nickname;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.Builder;

@Builder
public record GetCreatorProfileQuery(
        Nickname nickname,
        @Nullable UserId userId
) {
    public static GetCreatorProfileQuery of(Nickname nickname, @Nullable UserId userId) {
        return new GetCreatorProfileQuery(nickname, userId);
    }
}
