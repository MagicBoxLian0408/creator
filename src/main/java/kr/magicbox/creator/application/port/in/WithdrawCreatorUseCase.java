package kr.magicbox.creator.application.port.in;

import kr.magicbox.creator.application.dto.command.WithdrawCreatorCommand;

public interface WithdrawCreatorUseCase {

    void withdrawCreator(WithdrawCreatorCommand command);
}
