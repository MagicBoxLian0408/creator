package kr.magicbox.creator.application.service.certification;

import kr.magicbox.creator.application.dto.command.ReviewCreatorCertificationCommand;
import kr.magicbox.creator.application.port.in.ReviewCreatorCertificationUseCase;
import kr.magicbox.creator.application.port.out.CreatorCertificationRepositoryPort;
import kr.magicbox.creator.application.port.out.CreatorOutboxRepositoryPort;
import kr.magicbox.creator.application.port.out.CreatorRepositoryPort;
import kr.magicbox.creator.application.port.out.UserNicknameQueryPort;
import kr.magicbox.creator.application.port.out.UserProfileImageQueryPort;
import kr.magicbox.creator.domain.aggregate.Creator;
import kr.magicbox.creator.domain.aggregate.CreatorCertification;
import kr.magicbox.creator.domain.event.CreatorCertificationApprovedEvent;
import kr.magicbox.creator.domain.event.CreatorCertificationRejectedEvent;
import kr.magicbox.creator.domain.exception.CreatorAlreadyExistsException;
import kr.magicbox.creator.domain.exception.CreatorCertificationNotFoundException;
import kr.magicbox.creator.domain.vo.CreatorCertificationResult;
import kr.magicbox.creator.domain.vo.Nickname;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewCreatorCertificationService implements ReviewCreatorCertificationUseCase {

    private final CreatorCertificationRepositoryPort certificationRepositoryPort;
    private final CreatorRepositoryPort creatorRepositoryPort;
    private final CreatorOutboxRepositoryPort eventRepositoryPort;
    private final UserNicknameQueryPort userNicknameQueryPort;
    private final UserProfileImageQueryPort userProfileImageQueryPort;

    @Transactional
    @Override
    public void reviewCreatorCertification(ReviewCreatorCertificationCommand command) {
        CreatorCertification certification = certificationRepositoryPort.findById(command.certificationId())
                .orElseThrow(CreatorCertificationNotFoundException::new);

        certification.review(command.certificationStatus().toStatus(), CreatorCertificationResult.of(command.reviewerId(), command.reviewMessage()));
        certificationRepositoryPort.update(certification);

        if (certification.isApproved()) {
            Creator creator = createCreator(certification);
            eventRepositoryPort.save(buildApprovedEvent(certification, creator));
        }
        else {
            eventRepositoryPort.save(buildRejectedEvent(certification));
        }
    }

    private CreatorCertificationApprovedEvent buildApprovedEvent(CreatorCertification certification, Creator creator) {
        return CreatorCertificationApprovedEvent.builder()
                .userId(certification.getUserId())
                .creatorId(creator.getId())
                .certificationId(certification.getId())
                .nickname(creator.getNicknameValue())
                .genres(creator.getGenres())
                .status(creator.getStatus())
                .occurredAt(certification.getResult().reviewedAt())
                .build();
    }

    private CreatorCertificationRejectedEvent buildRejectedEvent(CreatorCertification certification) {
        return CreatorCertificationRejectedEvent.builder()
                .userId(certification.getUserId())
                .certificationId(certification.getId())
                .reviewMessage(certification.getResult().reviewMessage())
                .occurredAt(certification.getResult().reviewedAt())
                .build();
    }

    private Creator createCreator(CreatorCertification certification) {
        if (creatorRepositoryPort.existsByUserId(certification.getUserId())) {
            throw new CreatorAlreadyExistsException();
        }
        String nickname = userNicknameQueryPort.getNickname(certification.getUserId());
        String profileImageUrl = userProfileImageQueryPort.getProfileImageUrl(certification.getUserId());

        Creator creator = Creator.createBuilder()
                .userId(certification.getUserId())
                .nickname(Nickname.of(nickname))
                .profileImageUrl(profileImageUrl)
                .genres(certification.getRequest().genres())
                .build();

        return creatorRepositoryPort.save(creator);
    }
}
