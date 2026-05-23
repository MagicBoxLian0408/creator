package kr.magicbox.creator.domain.exception;

import kr.magicbox.creator.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class CreatorCertificationPendingAlreadyExistsException extends BusinessException {
    public CreatorCertificationPendingAlreadyExistsException() {
        super("이미 심사 대기 중인 인증 신청이 존재합니다.", HttpStatus.CONFLICT);
    }
}
