package kr.magicbox.creator.application.dto.query;

import lombok.Builder;

@Builder
public record GetAllCreatorsQuery(
        Long cursorId,
        int size
) {
    public static GetAllCreatorsQuery of(Long cursorId, int size) {
        return new GetAllCreatorsQuery(cursorId, size);
    }
}
