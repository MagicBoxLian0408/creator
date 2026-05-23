package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.application.dto.command.UpdateCreatorProfileCommand;

public interface UpdateCreatorProfileUseCase {

    void updateCreatorProfile(UpdateCreatorProfileCommand command);
}
