package kr.magicbox.creator.domain.exception;

import kr.magicbox.creator.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class InvalidReviewRatingException extends BusinessException {

    public InvalidReviewRatingException() {
        super("별점은 0.0 이상 5.0 이하여야 합니다.", HttpStatus.BAD_REQUEST);
    }

    public InvalidReviewRatingException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
