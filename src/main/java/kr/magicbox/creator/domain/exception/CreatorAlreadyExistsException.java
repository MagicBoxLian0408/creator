package kr.magicbox.creator.domain.exception;

import kr.magicbox.creator.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class CreatorAlreadyExistsException extends BusinessException {

    public CreatorAlreadyExistsException() {
        super("이미 등록된 크리에이터입니다.", HttpStatus.CONFLICT);
    }
}
