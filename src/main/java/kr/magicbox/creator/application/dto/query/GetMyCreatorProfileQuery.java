package kr.magicbox.creator.application.dto.query;

import kr.magicbox.creator.domain.vo.UserId;
import lombok.Builder;

@Builder
public record GetMyCreatorProfileQuery(
        UserId userId
) {
    public static GetMyCreatorProfileQuery of(UserId userId) {
        return new GetMyCreatorProfileQuery(userId);
    }
}
