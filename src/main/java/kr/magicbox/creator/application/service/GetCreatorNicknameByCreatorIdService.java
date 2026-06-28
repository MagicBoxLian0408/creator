package kr.magicbox.creator.application.service;

import kr.magicbox.creator.application.port.in.GetCreatorNicknameByCreatorIdUseCase;
import kr.magicbox.creator.application.port.out.CreatorRepositoryPort;
import kr.magicbox.creator.domain.exception.CreatorNotFoundException;
import kr.magicbox.creator.domain.vo.CreatorId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetCreatorNicknameByCreatorIdService implements GetCreatorNicknameByCreatorIdUseCase {

    private final CreatorRepositoryPort creatorRepositoryPort;

    @Transactional(readOnly = true)
    @Override
    public String getCreatorNickname(CreatorId creatorId) {
        return creatorRepositoryPort.findByCreatorId(creatorId)
                .orElseThrow(CreatorNotFoundException::new)
                .getNicknameValue();
    }
}
