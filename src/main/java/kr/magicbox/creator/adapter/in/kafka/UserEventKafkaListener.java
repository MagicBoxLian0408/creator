package kr.magicbox.creator.adapter.in.kafka;

import kr.magicbox.creator.adapter.in.kafka.annotation.Idempotent;
import kr.magicbox.creator.adapter.in.kafka.event.UserBannedEvent;
import kr.magicbox.creator.adapter.in.kafka.event.UserWithdrawnEvent;
import kr.magicbox.creator.adapter.out.persistence.entity.CreatorInboxEntity;
import kr.magicbox.creator.adapter.out.persistence.repository.CreatorInboxRepository;
import kr.magicbox.creator.application.port.in.HandleUserBannedUseCase;
import kr.magicbox.creator.application.port.in.HandleUserWithdrawnUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventKafkaListener {

    private final HandleUserWithdrawnUseCase handleUserWithdrawnUseCase;
    private final HandleUserBannedUseCase handleUserBannedUseCase;
    private final CreatorInboxRepository creatorInboxRepository;

    @Idempotent
    @RetryableTopic
    @KafkaListener(topics = "outbox.event.user-withdrawn", groupId = "creator-service")
    public void handleUserWithdrawnEvent(ConsumerRecord<String, UserWithdrawnEvent> consumerRecord) {
        log.info("[Inbox] user-withdrawn 이벤트 수신. key={}", consumerRecord.key());
        handleUserWithdrawnUseCase.handleUserWithdrawn(consumerRecord.value().userId());
    }

    @Idempotent
    @RetryableTopic
    @KafkaListener(topics = "outbox.event.user-banned", groupId = "creator-service")
    public void handleUserBannedEvent(ConsumerRecord<String, UserBannedEvent> consumerRecord) {
        log.info("[Inbox] user-banned 이벤트 수신. key={}", consumerRecord.key());
        handleUserBannedUseCase.handleUserBanned(consumerRecord.value().userId());
    }

    @DltHandler
    public void handleDlt(ConsumerRecord<String, ?> consumerRecord) {
        log.error("[Inbox] DLT 전환. topic={}, partition={}, offset={}", consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset());
        creatorInboxRepository.findByTopicAndPartitionAndOffset(consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset())
                .ifPresent(CreatorInboxEntity::markDeadLettered);
    }

}
