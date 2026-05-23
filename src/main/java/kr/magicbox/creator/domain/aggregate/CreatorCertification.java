package kr.magicbox.creator.domain.aggregate;

import kr.magicbox.creator.domain.enums.CreatorCertificationStatus;
import kr.magicbox.creator.domain.exception.CreatorCertificationAlreadyReviewedException;
import kr.magicbox.creator.domain.exception.CreatorCertificationNotFoundException;
import kr.magicbox.creator.domain.exception.InvalidCreatorCertificationReviewStatusException;
import kr.magicbox.creator.domain.exception.InvalidFieldException;
import kr.magicbox.creator.domain.vo.CreatorCertificationId;
import kr.magicbox.creator.domain.vo.CreatorCertificationRequest;
import kr.magicbox.creator.domain.vo.CreatorCertificationResult;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatorCertification {

    private final CreatorCertificationId id;
    private final UserId userId;
    private final CreatorCertificationRequest request;
    private CreatorCertificationStatus status;
    private CreatorCertificationResult result;

    @Builder(builderMethodName = "createBuilder", builderClassName = "CreateBuilder")
    public CreatorCertification(UserId userId, CreatorCertificationRequest request) {
        if (request == null) {
            throw new InvalidFieldException("심사 신청 정보는 필수 값입니다.");
        }
        this.id = null;
        this.userId = userId;
        this.request = request;
        this.status = CreatorCertificationStatus.PENDING;
        this.result = null;
    }

    @Builder(builderMethodName = "reconstructBuilder", builderClassName = "ReconstructBuilder")
    public CreatorCertification(CreatorCertificationId id, UserId userId, CreatorCertificationRequest request,
                                CreatorCertificationStatus status, CreatorCertificationResult result) {
        if (id == null) throw new InvalidFieldException("인증 심사 ID는 필수 값입니다.");
        if (request == null) throw new InvalidFieldException("심사 신청 정보는 필수 값입니다.");
        this.id = id;
        this.userId = userId;
        this.request = request;
        this.status = status != null ? status : CreatorCertificationStatus.PENDING;
        this.result = result;
    }

    public boolean isReviewed() {
        return this.status != CreatorCertificationStatus.PENDING;
    }

    public boolean isApproved() {
        return this.status == CreatorCertificationStatus.APPROVED;
    }

    public void review(CreatorCertificationStatus status, CreatorCertificationResult result) {
        if (isReviewed()) {
            throw new CreatorCertificationAlreadyReviewedException();
        }
        if (status == CreatorCertificationStatus.PENDING) {
            throw new InvalidCreatorCertificationReviewStatusException();
        }
        this.status = status;
        this.result = result;
    }

    public void cancel(UserId requestUserId) {
        if (isReviewed()) {
            throw new CreatorCertificationAlreadyReviewedException("이미 심사가 완료된 인증 신청은 취소할 수 없습니다.");
        }
        if (!this.userId.equals(requestUserId)) {
            throw new CreatorCertificationNotFoundException();
        }
        this.status = CreatorCertificationStatus.CANCELLED;
    }
}
