package kr.magicbox.creator.application.dto.command;

import kr.magicbox.creator.domain.enums.MagicGenre;
import kr.magicbox.creator.domain.vo.UserId;

import java.util.Set;

public record ApplyCreatorCertificationCommand(
        UserId userId,
        Set<MagicGenre> genres,
        String portfolioUrl
) {
}