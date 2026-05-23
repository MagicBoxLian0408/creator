package kr.magicbox.creator.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.magicbox.creator.application.dto.command.UpdateCreatorProfileCommand;
import kr.magicbox.creator.domain.constants.CreatorPolicyConstants;
import kr.magicbox.creator.domain.enums.MagicGenre;
import kr.magicbox.creator.domain.vo.Nickname;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.Builder;

import java.util.Set;

@Builder
public record UpdateCreatorProfileRequest(
        @NotNull(message = "닉네임은 필수입니다.")
        @Size(min = CreatorPolicyConstants.NICKNAME_MIN_LENGTH, max = CreatorPolicyConstants.NICKNAME_MAX_LENGTH, message = "닉네임은 2자 이상 20자 이하여야 합니다.")
        String nickname,
        @Size(max = CreatorPolicyConstants.TAGLINE_MAX_LENGTH, message = "태그라인은 50자 이하여야 합니다.")
        String tagline,
        String profileImageUrl,
        @Size(max = CreatorPolicyConstants.INTRODUCTION_MAX_LENGTH, message = "소개는 500자 이하여야 합니다.")
        String introduction,
        @NotEmpty(message = "장르는 최소 1개 이상 선택해야 합니다.")
        Set<@NotNull(message = "장르 값은 null일 수 없습니다.") MagicGenre> genres
) {

    public UpdateCreatorProfileCommand toCommand(UserId userId) {
        return new UpdateCreatorProfileCommand(
                userId,
                nickname == null ? null : Nickname.of(nickname),
                tagline,
                profileImageUrl,
                introduction,
                genres
        );
    }
}
