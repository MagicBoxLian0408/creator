package kr.magicbox.creator.application.service;

import kr.magicbox.creator.application.port.in.HandleUserWithdrawnUseCase;
import kr.magicbox.creator.application.port.out.CreatorOutboxRepositoryPort;
import kr.magicbox.creator.application.port.out.CreatorRepositoryPort;
import kr.magicbox.creator.domain.aggregate.Creator;
import kr.magicbox.creator.domain.event.CreatorRevokedEvent;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HandleUserWithdrawnService implements HandleUserWithdrawnUseCase {

    private final CreatorRepositoryPort creatorRepositoryPort;
    private final CreatorOutboxRepositoryPort creatorOutboxRepositoryPort;

    @Override
    @Transactional
    public void handleUserWithdrawn(UserId userId) {
        Optional<Creator> creatorOpt = creatorRepositoryPort.findByUserId(userId);
        if (creatorOpt.isEmpty()) return;
        Creator creator = creatorOpt.get();
        creator.delete();
        creatorRepositoryPort.update(creator);
        creatorOutboxRepositoryPort.save(
                CreatorRevokedEvent.builder()
                        .creatorId(creator.getId())
                        .occurredAt(Instant.now())
                        .build()
        );
    }
}
