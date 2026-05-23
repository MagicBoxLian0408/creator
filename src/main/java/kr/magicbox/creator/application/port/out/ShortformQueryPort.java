package kr.magicbox.creator.application.port.out;

import kr.magicbox.creator.application.dto.result.ShortformResult;

import java.util.List;

public interface ShortformQueryPort {

    List<ShortformResult> getShortforms(Long creatorId);
}
