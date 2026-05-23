package kr.magicbox.creator.domain.exception;

import kr.magicbox.creator.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class CreatorNotFoundException extends BusinessException {

    public CreatorNotFoundException() {
        super("크리에이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
