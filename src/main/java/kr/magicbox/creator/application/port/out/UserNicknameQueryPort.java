package kr.magicbox.creator.application.port.out;

import kr.magicbox.creator.domain.vo.UserId;

import java.util.concurrent.CompletableFuture;

public interface UserNicknameQueryPort {

    CompletableFuture<String> getNickname(UserId userId);
}
