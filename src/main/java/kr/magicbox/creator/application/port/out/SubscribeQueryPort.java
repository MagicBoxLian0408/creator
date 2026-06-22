package kr.magicbox.creator.application.port.out;

import java.util.concurrent.CompletableFuture;

public interface SubscribeQueryPort {

    CompletableFuture<Long> getSubscriberCount(Long creatorId);

    CompletableFuture<Boolean> isSubscribed(Long creatorId, Long userId);
}
