package kr.magicbox.creator.application.dto.command;

import kr.magicbox.creator.domain.vo.CreatorId;
import lombok.Builder;

@Builder
public record UnbanCreatorCommand(
        CreatorId creatorId
) {
    public static UnbanCreatorCommand of(CreatorId creatorId) {
        return new UnbanCreatorCommand(creatorId);
    }
}
