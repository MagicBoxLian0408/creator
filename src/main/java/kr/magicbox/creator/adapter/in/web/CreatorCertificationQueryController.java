package kr.magicbox.creator.adapter.in.web;

import kr.magicbox.creator.adapter.in.web.constants.CursorConstants;
import kr.magicbox.creator.adapter.in.web.dto.response.CreatorCertificationInfoResponse;
import kr.magicbox.creator.adapter.in.web.dto.response.CursorResponse;
import kr.magicbox.creator.adapter.in.web.validation.CursorSize;
import kr.magicbox.creator.application.dto.query.GetMyCreatorCertificationsQuery;
import kr.magicbox.creator.application.port.in.GetMyCreatorCertificationsUseCase;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/certification")
@RequiredArgsConstructor
@Validated
public class CreatorCertificationQueryController {

    private final GetMyCreatorCertificationsUseCase getMyCreatorCertificationsUseCase;

    @GetMapping("/me")
    public ResponseEntity<CursorResponse<CreatorCertificationInfoResponse>> getMyCreatorCertifications(
            @AuthenticationPrincipal UserId userId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = CursorConstants.DEFAULT_SIZE) @CursorSize Integer size) {
        List<CreatorCertificationInfoResponse> content =
                getMyCreatorCertificationsUseCase.getMyCreatorCertifications(GetMyCreatorCertificationsQuery.of(userId, cursor, size + 1))
                        .stream()
                        .map(CreatorCertificationInfoResponse::from)
                        .toList();
        return ResponseEntity.ok(CursorResponse.of(content, size, CreatorCertificationInfoResponse::certificationId));
    }
}
