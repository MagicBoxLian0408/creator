package kr.magicbox.creator.adapter.out.persistence.repository;

import kr.magicbox.creator.adapter.out.persistence.entity.CreatorInboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreatorInboxRepository extends JpaRepository<CreatorInboxEntity, Long> {

    boolean existsByKey(String key);

    Optional<CreatorInboxEntity> findByTopicAndPartitionAndOffset(String topic, Integer partition, Long offset);
}