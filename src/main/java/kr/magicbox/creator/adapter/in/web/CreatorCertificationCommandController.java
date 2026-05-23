package kr.magicbox.creator.adapter.in.web;

import kr.magicbox.creator.adapter.in.web.dto.request.ApplyCreatorCertificationRequest;
import kr.magicbox.creator.application.dto.command.CancelCreatorCertificationCommand;
import kr.magicbox.creator.application.port.in.ApplyCreatorCertificationUseCase;
import kr.magicbox.creator.application.port.in.CancelCreatorCertificationUseCase;
import kr.magicbox.creator.domain.vo.CreatorCertificationId;
import kr.magicbox.creator.domain.vo.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/creator/certification")
@RequiredArgsConstructor
@Validated
public class CreatorCertificationCommandController {

    private final ApplyCreatorCertificationUseCase applyCreatorCertificationUseCase;
    private final CancelCreatorCertificationUseCase cancelCreatorCertificationUseCase;

    @PostMapping
    public ResponseEntity<Void> apply(
            @AuthenticationPrincipal UserId userId,
            @Valid @RequestBody ApplyCreatorCertificationRequest request
    ) {
        applyCreatorCertificationUseCase.applyCreatorCertification(request.toCommand(userId));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{creatorCertificationId}")
    public ResponseEntity<Void> cancel(
            @AuthenticationPrincipal UserId userId,
            @PathVariable Long creatorCertificationId
    ) {
        cancelCreatorCertificationUseCase.cancelCreatorCertification(
                CancelCreatorCertificationCommand.of(
                        CreatorCertificationId.of(creatorCertificationId),
                        userId
                )
        );
        return ResponseEntity.noContent().build();
    }
}
