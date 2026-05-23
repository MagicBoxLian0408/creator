package kr.magicbox.creator.adapter.in.web.dto.request;

import kr.magicbox.creator.application.dto.command.ReviewDecisionCommand;

public enum ReviewDecisionRequest {
    APPROVED, REJECTED;

    public ReviewDecisionCommand toCommand() {
        return ReviewDecisionCommand.valueOf(this.name());
    }
}