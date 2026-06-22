package kr.magicbox.creator.application.service;

import kr.magicbox.creator.application.dto.command.WithdrawCreatorCommand;
import kr.magicbox.creator.application.port.in.WithdrawCreatorUseCase;
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
public class WithdrawCreatorService implements WithdrawCreatorUseCase {
    private final CreatorRepositoryPort creatorRepositoryPort;
    private final CreatorOutboxRepositoryPort eventRepositoryPort;

    @Override
    @Transactional
    public void withdrawCreator(WithdrawCreatorCommand command) {
        Creator creator = creatorRepositoryPort.findByUserId(command.userId()).orElseThrow(CreatorNotFoundException::new);
        creator.delete();

        creatorRepositoryPort.update(creator);
        eventRepositoryPort.save(
                CreatorRevokedEvent.builder()
                        .creatorId(creator.getId())
                        .occurredAt(Instant.now())
                        .build()
        );
    }
}
