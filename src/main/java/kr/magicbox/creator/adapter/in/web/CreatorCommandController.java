package kr.magicbox.creator.adapter.in.web;

import kr.magicbox.creator.adapter.in.web.dto.request.UpdateCreatorProfileRequest;
import kr.magicbox.creator.application.dto.command.WithdrawCreatorCommand;
import kr.magicbox.creator.application.port.in.UpdateCreatorProfileUseCase;
import kr.magicbox.creator.application.port.in.WithdrawCreatorUseCase;
import kr.magicbox.creator.domain.vo.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class CreatorCommandController {

    private final UpdateCreatorProfileUseCase updateCreatorProfileUseCase;
    private final WithdrawCreatorUseCase withdrawCreatorUseCase;

    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfile(
            @AuthenticationPrincipal UserId userId,
            @Valid @RequestBody UpdateCreatorProfileRequest request
    ) {
        updateCreatorProfileUseCase.updateCreatorProfile(request.toCommand(userId));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> withdrawCreator(@AuthenticationPrincipal UserId userId) {
        withdrawCreatorUseCase.withdrawCreator(WithdrawCreatorCommand.of(userId));
        return ResponseEntity.noContent().build();
    }
}
