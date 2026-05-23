package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.application.dto.command.CancelCreatorCertificationCommand;

public interface CancelCreatorCertificationUseCase {

    void cancelCreatorCertification(CancelCreatorCertificationCommand command);
}
