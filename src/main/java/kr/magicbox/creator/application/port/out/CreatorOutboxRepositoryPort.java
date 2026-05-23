package kr.magicbox.creator.application.port.out;

import kr.magicbox.creator.domain.event.CreatorDomainEvent;

public interface CreatorOutboxRepositoryPort {
    void save(CreatorDomainEvent event);
}
