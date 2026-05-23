package kr.magicbox.creator.domain.event;

public interface CreatorDomainEvent {
    String key();
    CreatorDomainEventType eventType();
}