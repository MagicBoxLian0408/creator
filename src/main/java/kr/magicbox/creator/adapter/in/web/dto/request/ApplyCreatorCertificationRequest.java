package kr.magicbox.creator.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kr.magicbox.creator.application.dto.command.ApplyCreatorCertificationCommand;
import kr.magicbox.creator.domain.enums.MagicGenre;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.Builder;

import java.util.Set;

@Builder
public record ApplyCreatorCertificationRequest(
        @NotEmpty(message = "장르는 최소 1개 이상 선택해야 합니다.") Set<@NotNull(message = "장르 값은 null일 수 없습니다.") MagicGenre> genres,
        @NotBlank(message = "포트폴리오 URL은 필수입니다.") String portfolioUrl
) {

    public ApplyCreatorCertificationCommand toCommand(UserId userId) {
        return new ApplyCreatorCertificationCommand(
                userId,
                genres,
                portfolioUrl
        );
    }
}
