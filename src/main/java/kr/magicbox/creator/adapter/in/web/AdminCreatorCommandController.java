package kr.magicbox.creator.adapter.in.web;

import kr.magicbox.creator.application.dto.command.BanCreatorCommand;
import kr.magicbox.creator.application.dto.command.UnbanCreatorCommand;
import kr.magicbox.creator.application.port.in.BanCreatorUseCase;
import kr.magicbox.creator.application.port.in.UnbanCreatorUseCase;
import kr.magicbox.creator.domain.vo.CreatorId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminCreatorCommandController {
    private final BanCreatorUseCase banCreatorUseCase;
    private final UnbanCreatorUseCase unbanCreatorUseCase;

    @PatchMapping("/{creatorId}/ban")
    public ResponseEntity<Void> banCreator(@PathVariable Long creatorId) {
        banCreatorUseCase.banCreator(BanCreatorCommand.of(CreatorId.of(creatorId)));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{creatorId}/unban")
    public ResponseEntity<Void> unbanCreator(@PathVariable Long creatorId) {
        unbanCreatorUseCase.unbanCreator(UnbanCreatorCommand.of(CreatorId.of(creatorId)));
        return ResponseEntity.noContent().build();
    }
}
