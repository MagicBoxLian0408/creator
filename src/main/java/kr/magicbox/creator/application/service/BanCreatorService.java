package kr.magicbox.creator.application.service;

import kr.magicbox.creator.application.dto.command.BanCreatorCommand;
import kr.magicbox.creator.application.port.in.BanCreatorUseCase;
import kr.magicbox.creator.application.port.out.CreatorOutboxRepositoryPort;
import kr.magicbox.creator.application.port.out.CreatorRepositoryPort;
import kr.magicbox.creator.domain.aggregate.Creator;
import kr.magicbox.creator.domain.event.CreatorRevokedEvent;
import kr.magicbox.creator.domain.exception.CreatorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class BanCreatorService implements BanCreatorUseCase {

    private final CreatorRepositoryPort creatorRepositoryPort;
    private final CreatorOutboxRepositoryPort eventRepositoryPort;

    @Override
    @Transactional
    public void banCreator(BanCreatorCommand command) {
        Creator creator = creatorRepositoryPort.findById(command.creatorId().value())
                .orElseThrow(CreatorNotFoundException::new);

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
