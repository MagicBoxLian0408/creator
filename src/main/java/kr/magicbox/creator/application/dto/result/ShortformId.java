package kr.magicbox.creator.application.dto.result;

import kr.magicbox.creator.domain.exception.InvalidFieldException;

public record ShortformId(Long value) {

    public ShortformId {
        if (value == null || value <= 0) {
            throw new InvalidFieldException("숏폼 ID는 양수여야 합니다.");
        }
    }

    public static ShortformId of(Long value) {
        return new ShortformId(value);
    }
}