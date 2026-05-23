package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.application.dto.command.BanCreatorCommand;

public interface BanCreatorUseCase {

    void banCreator(BanCreatorCommand command);
}
