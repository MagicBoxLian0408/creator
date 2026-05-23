package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.application.dto.command.UnbanCreatorCommand;

public interface UnbanCreatorUseCase {

    void unbanCreator(UnbanCreatorCommand command);
}
