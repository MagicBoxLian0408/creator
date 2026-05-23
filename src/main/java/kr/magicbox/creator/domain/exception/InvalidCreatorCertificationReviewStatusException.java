package kr.magicbox.creator.domain.exception;

import kr.magicbox.creator.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class InvalidCreatorCertificationReviewStatusException extends BusinessException {

    public InvalidCreatorCertificationReviewStatusException() {
        super("심사 상태는 PENDING일 수 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
