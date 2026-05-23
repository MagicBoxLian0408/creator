package kr.magicbox.creator.application.dto.query;

import lombok.Builder;

@Builder
public record SearchCreatorsQuery(
        String keyword,
        Long cursorId,
        int size
) {
    public static SearchCreatorsQuery of(String keyword, Long cursorId, int size) {
        return new SearchCreatorsQuery(keyword, cursorId, size);
    }
}
