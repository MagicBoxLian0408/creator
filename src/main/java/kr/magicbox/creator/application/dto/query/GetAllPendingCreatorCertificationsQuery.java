package kr.magicbox.creator.application.dto.query;

import lombok.Builder;

@Builder
public record GetAllPendingCreatorCertificationsQuery(
        Long cursorId,
        int size
) {
    public static GetAllPendingCreatorCertificationsQuery of(Long cursorId, int size) {
        return new GetAllPendingCreatorCertificationsQuery(cursorId, size);
    }
}
