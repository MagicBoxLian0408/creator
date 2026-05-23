package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.application.dto.command.ApplyCreatorCertificationCommand;

public interface ApplyCreatorCertificationUseCase {

    void applyCreatorCertification(ApplyCreatorCertificationCommand command);
}