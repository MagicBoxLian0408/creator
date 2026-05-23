package kr.magicbox.creator.application.port.out;

public interface SubscribeQueryPort {

    long getSubscriberCount(Long creatorId);

    boolean isSubscribed(Long creatorId, Long userId);
}
