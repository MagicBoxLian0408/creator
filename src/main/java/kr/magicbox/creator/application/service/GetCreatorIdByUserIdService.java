package kr.magicbox.creator.application.service;

import kr.magicbox.creator.application.port.in.GetCreatorIdByUserIdUseCase;
import kr.magicbox.creator.application.port.out.CreatorRepositoryPort;
import kr.magicbox.creator.domain.aggregate.Creator;
import kr.magicbox.creator.domain.exception.CreatorNotFoundException;
import kr.magicbox.creator.domain.vo.CreatorId;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetCreatorIdByUserIdService implements GetCreatorIdByUserIdUseCase {

    private final CreatorRepositoryPort creatorRepositoryPort;

    @Transactional(readOnly = true)
    @Override
    public CreatorId getCreatorIdByUserId(UserId userId) {
        Creator creator = creatorRepositoryPort.findByUserId(userId)
                .orElseThrow(CreatorNotFoundException::new);
        return creator.getId();
    }
}
