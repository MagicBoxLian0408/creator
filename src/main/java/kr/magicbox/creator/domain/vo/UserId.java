package kr.magicbox.creator.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.magicbox.creator.domain.exception.InvalidFieldException;

public record UserId(@JsonValue Long value) {

    public UserId {
        if (value == null || value <= 0) {
            throw new InvalidFieldException("사용자 ID는 양수여야 합니다.");
        }
    }

    public static UserId of(Long value) {
        return new UserId(value);
    }
}