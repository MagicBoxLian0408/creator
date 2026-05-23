package kr.magicbox.creator.application.dto.command;

import kr.magicbox.creator.domain.vo.UserId;
import lombok.Builder;

@Builder
public record WithdrawCreatorCommand(
        UserId userId
) {
    public static WithdrawCreatorCommand of(UserId userId) {
        return new WithdrawCreatorCommand(userId);
    }
}
