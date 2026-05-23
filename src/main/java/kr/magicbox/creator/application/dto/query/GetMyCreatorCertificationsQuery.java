package kr.magicbox.creator.application.dto.query;

import kr.magicbox.creator.domain.vo.UserId;
import lombok.Builder;

@Builder
public record GetMyCreatorCertificationsQuery(
        UserId userId,
        Long cursorId,
        int size
) {
    public static GetMyCreatorCertificationsQuery of(UserId userId, Long cursorId, int size) {
        return new GetMyCreatorCertificationsQuery(userId, cursorId, size);
    }
}
