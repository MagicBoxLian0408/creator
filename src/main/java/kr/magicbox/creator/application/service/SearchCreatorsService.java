package kr.magicbox.creator.application.service;

import kr.magicbox.creator.application.dto.result.CreatorSearchResult;
import kr.magicbox.creator.application.dto.query.SearchCreatorsQuery;
import kr.magicbox.creator.application.port.in.SearchCreatorsUseCase;
import kr.magicbox.creator.application.port.out.CreatorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchCreatorsService implements SearchCreatorsUseCase {

    private final CreatorRepositoryPort creatorRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public List<CreatorSearchResult> searchCreators(SearchCreatorsQuery query) {
        return creatorRepositoryPort.searchByNickname(query.keyword(), query.cursorId(), query.size())
                .stream()
                .map(CreatorSearchResult::from)
                .toList();
    }
}
