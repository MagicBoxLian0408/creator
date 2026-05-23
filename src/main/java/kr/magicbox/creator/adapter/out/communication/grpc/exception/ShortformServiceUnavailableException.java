package kr.magicbox.creator.adapter.out.communication.grpc.exception;

import kr.magicbox.creator.global.exception.SystemError;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class ShortformServiceUnavailableException extends SystemError {
    public ShortformServiceUnavailableException(Throwable cause) {
        super("숏폼 서비스 호출을 할 수 없습니다.", HttpStatus.SERVICE_UNAVAILABLE, cause);
    }
}
