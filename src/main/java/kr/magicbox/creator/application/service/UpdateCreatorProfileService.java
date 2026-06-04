package kr.magicbox.creator.application.service;

import kr.magicbox.creator.application.dto.command.UpdateCreatorProfileCommand;
import kr.magicbox.creator.application.port.in.UpdateCreatorProfileUseCase;
import kr.magicbox.creator.application.port.out.CreatorOutboxRepositoryPort;
import kr.magicbox.creator.application.port.out.CreatorRepositoryPort;
import kr.magicbox.creator.domain.aggregate.Creator;
import kr.magicbox.creator.domain.event.CreatorProfileUpdatedEvent;
import kr.magicbox.creator.domain.event.CreatorProfileUpdatedEvent.ProfileSnapshot;
import kr.magicbox.creator.domain.exception.CreatorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UpdateCreatorProfileService implements UpdateCreatorProfileUseCase {

    private final CreatorRepositoryPort creatorRepositoryPort;
    private final CreatorOutboxRepositoryPort creatorOutboxRepositoryPort;

    @Transactional
    @Override
    public void updateCreatorProfile(UpdateCreatorProfileCommand command) {
        Creator creator = creatorRepositoryPort.findByUserId(command.userId())
                .orElseThrow(CreatorNotFoundException::new);

        ProfileSnapshot before = new ProfileSnapshot(
                creator.getNicknameValue(),
                creator.getTagline(),
                creator.getProfileImageUrl()
        );

        creator.updateProfile(
                command.nickname(),
                command.tagline(),
                command.profileImageUrl(),
                command.introduction(),
                command.genres()
        );

        creatorRepositoryPort.update(creator);

        ProfileSnapshot after = new ProfileSnapshot(
                creator.getNicknameValue(),
                creator.getTagline(),
                creator.getProfileImageUrl()
        );

        creatorOutboxRepositoryPort.save(CreatorProfileUpdatedEvent.builder()
                .eventId(creator.getId().value())
                .creatorId(creator.getId())
                .before(before)
                .after(after)
                .occurredAt(Instant.now())
                .build());
    }
}
