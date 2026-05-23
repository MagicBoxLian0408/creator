package kr.magicbox.creator.application.dto.command;

import kr.magicbox.creator.domain.vo.CreatorId;
import lombok.Builder;

@Builder
public record BanCreatorCommand(
        CreatorId creatorId
) {
    public static BanCreatorCommand of(CreatorId creatorId) {
        return new BanCreatorCommand(creatorId);
    }
}
