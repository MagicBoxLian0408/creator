package kr.magicbox.creator.application.dto.result;

import kr.magicbox.creator.domain.exception.InvalidFieldException;

public record ReleaseId(Long value) {

    public ReleaseId {
        if (value == null || value <= 0) {
            throw new InvalidFieldException("릴리즈 ID는 양수여야 합니다.");
        }
    }

    public static ReleaseId of(Long value) {
        return new ReleaseId(value);
    }
}