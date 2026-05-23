package kr.magicbox.creator.domain.exception;

import kr.magicbox.creator.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class CreatorCertificationAlreadyReviewedException extends BusinessException {

    public CreatorCertificationAlreadyReviewedException() {
        super("이미 심사가 완료된 신청입니다.", HttpStatus.BAD_REQUEST);
    }

    public CreatorCertificationAlreadyReviewedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
