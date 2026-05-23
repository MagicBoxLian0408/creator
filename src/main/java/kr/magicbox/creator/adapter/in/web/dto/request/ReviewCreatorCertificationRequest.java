package kr.magicbox.creator.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.magicbox.creator.application.dto.command.ReviewCreatorCertificationCommand;
import kr.magicbox.creator.domain.vo.CreatorCertificationId;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.Builder;

@Builder
public record ReviewCreatorCertificationRequest(
        @NotNull(message = "심사 결정은 필수입니다.") ReviewDecisionRequest decision,
        @NotBlank(message = "심사 메시지는 필수입니다.") String reviewMessage
) {

    public ReviewCreatorCertificationCommand toCommand(UserId reviewerId, Long creatorCertificationId) {
        return new ReviewCreatorCertificationCommand(
                reviewerId,
                CreatorCertificationId.of(creatorCertificationId),
                decision.toCommand(),
                reviewMessage
        );
    }
}
