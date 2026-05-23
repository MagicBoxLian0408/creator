package kr.magicbox.creator.application.service;

import kr.magicbox.creator.application.dto.result.CreatorSearchResult;
import kr.magicbox.creator.application.dto.query.GetAllCreatorsQuery;
import kr.magicbox.creator.application.port.in.GetAllCreatorsUseCase;
import kr.magicbox.creator.application.port.out.CreatorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllCreatorsService implements GetAllCreatorsUseCase {

    private final CreatorRepositoryPort creatorRepositoryPort;

    @Transactional(readOnly = true)
    @Override
    public List<CreatorSearchResult> getAllCreators(GetAllCreatorsQuery query) {
        return creatorRepositoryPort.findAllByCursor(query.cursorId(), query.size())
                .stream()
                .map(CreatorSearchResult::from)
                .toList();
    }
}
