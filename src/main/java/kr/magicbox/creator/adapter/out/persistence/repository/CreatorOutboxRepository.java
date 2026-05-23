package kr.magicbox.creator.adapter.out.persistence.repository;

import kr.magicbox.creator.adapter.out.persistence.entity.CreatorOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorOutboxRepository extends JpaRepository<CreatorOutboxEntity, Long> {
}
