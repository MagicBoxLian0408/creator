package kr.magicbox.creator.adapter.in.web;

import kr.magicbox.creator.adapter.in.web.constants.CursorConstants;
import kr.magicbox.creator.adapter.in.web.dto.response.CursorResponse;
import kr.magicbox.creator.adapter.in.web.dto.response.PendingCreatorCertificationResponse;
import kr.magicbox.creator.adapter.in.web.validation.CursorSize;
import kr.magicbox.creator.application.dto.query.GetAllPendingCreatorCertificationsQuery;
import kr.magicbox.creator.application.port.in.GetAllPendingCreatorCertificationsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/creator/certification")
@RequiredArgsConstructor
@Validated
public class AdminCreatorCertificationQueryController {

    private final GetAllPendingCreatorCertificationsUseCase getAllPendingCreatorCertificationsUseCase;

    @GetMapping("/pending")
    public ResponseEntity<CursorResponse<PendingCreatorCertificationResponse>> getPendingCreatorCertifications(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = CursorConstants.DEFAULT_SIZE) @CursorSize Integer size
    ) {
        List<PendingCreatorCertificationResponse> content =
                getAllPendingCreatorCertificationsUseCase
                        .getAllPendingCreatorCertifications(GetAllPendingCreatorCertificationsQuery.of(cursor, size + 1))
                        .stream()
                        .map(PendingCreatorCertificationResponse::from)
                        .toList();

        return ResponseEntity.ok(CursorResponse.of(content, size, PendingCreatorCertificationResponse::certificationId));
    }
}
