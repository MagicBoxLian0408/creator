package kr.magicbox.creator.adapter.out.persistence;

import kr.magicbox.creator.adapter.out.persistence.entity.CreatorOutboxEntity;
import kr.magicbox.creator.adapter.out.persistence.repository.CreatorOutboxRepository;
import kr.magicbox.creator.application.port.out.CreatorOutboxRepositoryPort;
import kr.magicbox.creator.domain.event.CreatorDomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

@Repository
@RequiredArgsConstructor
public class CreatorOutboxAdapter implements CreatorOutboxRepositoryPort {

    private final CreatorOutboxRepository creatorOutboxRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void save(CreatorDomainEvent event) {
        String payload = objectMapper.writeValueAsString(event);
        creatorOutboxRepository.save(CreatorOutboxEntity.builder()
                .eventType(event.eventType().getValue())
                .key(event.key())
                .payload(payload)
                .build());
    }
}
