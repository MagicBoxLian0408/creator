package kr.magicbox.creator.application.service;

import kr.magicbox.creator.application.port.in.GetCreatorProfileByCreatorIdUseCase;
import kr.magicbox.creator.application.port.out.CreatorRepositoryPort;
import kr.magicbox.creator.domain.aggregate.Creator;
import kr.magicbox.creator.domain.exception.CreatorNotFoundException;
import kr.magicbox.creator.domain.vo.CreatorId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetCreatorProfileByCreatorIdService implements GetCreatorProfileByCreatorIdUseCase {

    private final CreatorRepositoryPort creatorRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public CreatorProfile getCreatorProfile(CreatorId creatorId) {
        Creator creator = creatorRepositoryPort.findByCreatorId(creatorId)
                .orElseThrow(CreatorNotFoundException::new);
        return new CreatorProfile(
                creator.getId().value(),
                creator.getNicknameValue(),
                creator.getProfileImageUrl()
        );
    }
}
