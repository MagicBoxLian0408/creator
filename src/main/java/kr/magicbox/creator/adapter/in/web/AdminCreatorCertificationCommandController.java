package kr.magicbox.creator.adapter.in.web;

import jakarta.validation.Valid;
import kr.magicbox.creator.adapter.in.web.dto.request.ReviewCreatorCertificationRequest;
import kr.magicbox.creator.application.port.in.ReviewCreatorCertificationUseCase;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/certification")
@RequiredArgsConstructor
@Validated
public class AdminCreatorCertificationCommandController {

    private final ReviewCreatorCertificationUseCase reviewCreatorCertificationUseCase;

    @PatchMapping("/{creatorCertificationId}/review")
    public ResponseEntity<Void> reviewCreatorCertification(
            @AuthenticationPrincipal UserId reviewerId,
            @PathVariable Long creatorCertificationId,
            @Valid @RequestBody ReviewCreatorCertificationRequest request
    ) {
        reviewCreatorCertificationUseCase.reviewCreatorCertification(request.toCommand(reviewerId, creatorCertificationId));
        return ResponseEntity.noContent().build();
    }
}
