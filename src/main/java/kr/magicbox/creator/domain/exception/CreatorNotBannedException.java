package kr.magicbox.creator.domain.exception;

import kr.magicbox.creator.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class CreatorNotBannedException extends BusinessException {
    public CreatorNotBannedException() {
        super("정지 상태가 아닌 크리에이터는 정지 해제할 수 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
