package kr.magicbox.creator.application.service;

import kr.magicbox.creator.application.port.in.HandleUserProfileUpdatedUseCase;
import kr.magicbox.creator.application.port.out.CreatorRepositoryPort;
import kr.magicbox.creator.domain.aggregate.Creator;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HandleUserProfileUpdatedService implements HandleUserProfileUpdatedUseCase {

    private final CreatorRepositoryPort creatorRepositoryPort;

    @Override
    @Transactional
    public void handleUserProfileUpdated(UserId userId, String profileImageUrl) {
        Optional<Creator> creatorOpt = creatorRepositoryPort.findByUserId(userId);
        if (creatorOpt.isEmpty()) return;
        Creator creator = creatorOpt.get();

        creator.updateProfile(null, null, profileImageUrl, null, null);
        creatorRepositoryPort.update(creator);
    }
}
