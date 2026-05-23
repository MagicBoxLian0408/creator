package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.application.dto.command.ReviewCreatorCertificationCommand;

public interface ReviewCreatorCertificationUseCase {

    void reviewCreatorCertification(ReviewCreatorCertificationCommand command);
}
