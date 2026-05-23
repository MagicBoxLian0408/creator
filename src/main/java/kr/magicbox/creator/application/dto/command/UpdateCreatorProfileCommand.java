package kr.magicbox.creator.application.dto.command;

import kr.magicbox.creator.domain.enums.MagicGenre;
import kr.magicbox.creator.domain.vo.Nickname;
import kr.magicbox.creator.domain.vo.UserId;

import java.util.Set;

public record UpdateCreatorProfileCommand(
        UserId userId,
        Nickname nickname,
        String tagline,
        String profileImageUrl,
        String introduction,
        Set<MagicGenre> genres
) {
}