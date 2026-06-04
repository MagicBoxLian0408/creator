package kr.magicbox.creator.application.service;

import kr.magicbox.creator.application.port.in.HandleUserBannedUseCase;
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
public class HandleUserBannedService implements HandleUserBannedUseCase {

    private final CreatorRepositoryPort creatorRepositoryPort;
    private final CreatorOutboxRepositoryPort eventRepositoryPort;

    @Override
    @Transactional
    public void handleUserBanned(UserId userId) {
        Optional<Creator> creatorOpt = creatorRepositoryPort.findByUserId(userId);
        if (creatorOpt.isEmpty()) return;
        Creator creator = creatorOpt.get();

        if (creator.isBanned()) {
            return;
        }

        creator.ban();
        creatorRepositoryPort.update(creator);
        eventRepositoryPort.save(
                CreatorRevokedEvent.builder()
                        .eventId(creator.getId().value())
                        .creatorId(creator.getId())
                        .occurredAt(Instant.now())
                        .build()
        );
    }
}
