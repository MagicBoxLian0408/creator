package kr.magicbox.creator.application.service;

import kr.magicbox.creator.application.dto.command.UpdateCreatorProfileCommand;
import kr.magicbox.creator.application.port.in.UpdateCreatorProfileUseCase;
import kr.magicbox.creator.application.port.out.CreatorRepositoryPort;
import kr.magicbox.creator.domain.aggregate.Creator;
import kr.magicbox.creator.domain.exception.CreatorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCreatorProfileService implements UpdateCreatorProfileUseCase {

    private final CreatorRepositoryPort creatorRepositoryPort;

    @Transactional
    @Override
    public void updateCreatorProfile(UpdateCreatorProfileCommand command) {
        Creator creator = creatorRepositoryPort.findByUserId(command.userId())
                .orElseThrow(CreatorNotFoundException::new);

        creator.updateProfile(
                command.nickname(),
                command.tagline(),
                command.profileImageUrl(),
                command.introduction(),
                command.genres()
        );

        creatorRepositoryPort.update(creator);
    }
}
