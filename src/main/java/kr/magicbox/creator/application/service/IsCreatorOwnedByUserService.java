package kr.magicbox.creator.application.service;

import kr.magicbox.creator.application.dto.query.IsCreatorOwnedByUserQuery;
import kr.magicbox.creator.application.port.in.IsCreatorOwnedByUserUseCase;
import kr.magicbox.creator.application.port.out.CreatorRepositoryPort;
import kr.magicbox.creator.domain.aggregate.Creator;
import kr.magicbox.creator.domain.exception.CreatorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IsCreatorOwnedByUserService implements IsCreatorOwnedByUserUseCase {
    private final CreatorRepositoryPort creatorRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public boolean isOwnedByUser(IsCreatorOwnedByUserQuery query) {
        Creator creator = creatorRepositoryPort.findByCreatorId(query.creatorId())
                .orElseThrow(CreatorNotFoundException::new);
        return creator.getUserIdValue().equals(query.userId().value());
    }
}