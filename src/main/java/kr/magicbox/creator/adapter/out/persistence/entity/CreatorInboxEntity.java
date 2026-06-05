package kr.magicbox.creator.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "creator_inbox")
public class CreatorInboxEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String kafkaKey;

    @Column(nullable = false)
    private String topic;

    @Column(name = "kafka_partition", nullable = false)
    private Integer partition;

    @Column(name = "kafka_offset", nullable = false)
    private Long offset;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CreatorInboxStatus status;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @Builder
    public CreatorInboxEntity(String kafkaKey, String topic, Integer partition, Long offset, CreatorInboxStatus status, Instant occurredAt) {
        this.kafkaKey = kafkaKey;
        this.topic = topic;
        this.partition = partition;
        this.offset = offset;
        this.status = status;
        this.occurredAt = occurredAt;
    }

    public void markProcessed() {
        this.status = CreatorInboxStatus.PROCESSED;
    }

    public void markDeadLettered() {
        this.status = CreatorInboxStatus.DEAD_LETTERED;
    }
}