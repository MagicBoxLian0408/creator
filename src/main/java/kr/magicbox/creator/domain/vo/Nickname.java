package kr.magicbox.creator.domain.vo;

import kr.magicbox.creator.domain.constants.CreatorPolicyConstants;
import kr.magicbox.creator.domain.exception.InvalidFieldException;

public record Nickname(String value) {

    public Nickname {
        validateNickname(value);
        value = value.trim();
    }

    public static Nickname of(String value) {
        return new Nickname(value);
    }

    private static void validateNickname(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new InvalidFieldException("닉네임은 필수 값입니다.");
        }

        String trimmed = nickname.trim();

        if (trimmed.length() < CreatorPolicyConstants.NICKNAME_MIN_LENGTH) {
            throw new InvalidFieldException("닉네임은 " + CreatorPolicyConstants.NICKNAME_MIN_LENGTH + "자 이상이어야 합니다.");
        }

        if (trimmed.length() > CreatorPolicyConstants.NICKNAME_MAX_LENGTH) {
            throw new InvalidFieldException("닉네임은 " + CreatorPolicyConstants.NICKNAME_MAX_LENGTH + "자 이내여야 합니다.");
        }
    }
}
