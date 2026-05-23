package kr.magicbox.creator.domain.vo;

import kr.magicbox.creator.domain.exception.InvalidFieldException;

public record CreatorCertificationId(Long value) {

    public CreatorCertificationId {
        if (value == null || value <= 0) {
            throw new InvalidFieldException("인증 ID는 양수여야 합니다.");
        }
    }

    public static CreatorCertificationId of(Long value) {
        return new CreatorCertificationId(value);
    }
}
