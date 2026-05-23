package kr.magicbox.creator.adapter.out.communication.grpc.exception;

import kr.magicbox.creator.global.exception.SystemError;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class UserServiceUnavailableException extends SystemError {
    public UserServiceUnavailableException(Throwable cause) {
        super("유저 서비스 호출을 할 수 없습니다.", HttpStatus.SERVICE_UNAVAILABLE, cause);
    }
}
