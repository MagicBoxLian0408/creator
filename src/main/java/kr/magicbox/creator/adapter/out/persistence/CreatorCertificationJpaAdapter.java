package kr.magicbox.creator.adapter.out.persistence;

import kr.magicbox.creator.adapter.out.persistence.entity.CreatorCertificationEntity;
import kr.magicbox.creator.adapter.out.persistence.mapper.CreatorCertificationMapper;
import kr.magicbox.creator.adapter.out.persistence.repository.CreatorCertificationJpaRepository;
import kr.magicbox.creator.application.port.out.CreatorCertificationRepositoryPort;
import kr.magicbox.creator.domain.aggregate.CreatorCertification;
import kr.magicbox.creator.domain.enums.CreatorCertificationStatus;
import kr.magicbox.creator.domain.exception.CreatorCertificationNotFoundException;
import kr.magicbox.creator.domain.vo.CreatorCertificationId;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreatorCertificationJpaAdapter implements CreatorCertificationRepositoryPort {

    private final CreatorCertificationJpaRepository creatorCertificationJpaRepository;
    private final CreatorCertificationMapper creatorCertificationMapper;

    @Override
    public void save(CreatorCertification certification) {
        CreatorCertificationEntity entity = creatorCertificationMapper.toEntity(certification);
        creatorCertificationJpaRepository.save(entity);
    }

    @Override
    public void update(CreatorCertification certification) {
        CreatorCertificationEntity entity = creatorCertificationJpaRepository.findById(certification.getId().value())
                .orElseThrow(CreatorCertificationNotFoundException::new);
        creatorCertificationMapper.updateEntity(certification, entity);
        creatorCertificationJpaRepository.save(entity);
    }

    @Override
    public Optional<CreatorCertification> findById(CreatorCertificationId id) {
        return creatorCertificationJpaRepository.findById(id.value())
                .map(creatorCertificationMapper::toDomain);
    }

    @Override
    public List<CreatorCertification> findAllByUserId(UserId userId) {
        return creatorCertificationJpaRepository.findAllByUserId(userId.value())
                .stream()
                .map(creatorCertificationMapper::toDomain)
                .toList();
    }

    @Override
    public List<CreatorCertification> findAllByUserIdByCursor(UserId userId, Long cursorId, int size) {
        return creatorCertificationJpaRepository.findAllByUserIdWithCursor(userId.value(), cursorId, size)
                .stream()
                .map(creatorCertificationMapper::toDomain)
                .toList();
    }

    @Override
    public List<CreatorCertification> findAllByStatusByCursor(CreatorCertificationStatus status, Long cursorId, int size) {
        return creatorCertificationJpaRepository.findAllByStatusWithCursor(status, cursorId, size)
                .stream()
                .map(creatorCertificationMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(CreatorCertificationId id) {
        creatorCertificationJpaRepository.deleteById(id.value());
    }

    @Override
    public boolean existsByUserIdAndStatus(UserId userId, CreatorCertificationStatus status) {
        return creatorCertificationJpaRepository.existsByUserIdAndStatus(userId.value(), status);
    }
}
