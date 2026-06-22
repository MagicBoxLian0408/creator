package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.application.dto.result.CreatorSearchResult;
import kr.magicbox.creator.application.dto.query.SearchCreatorsQuery;

import java.util.List;

public interface SearchCreatorsUseCase {

    List<CreatorSearchResult> searchCreators(SearchCreatorsQuery query);
}
