package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.application.dto.result.CreatorSearchResult;
import kr.magicbox.creator.application.dto.query.GetAllCreatorsQuery;

import java.util.List;

public interface GetAllCreatorsUseCase {

    List<CreatorSearchResult> getAllCreators(GetAllCreatorsQuery query);
}
