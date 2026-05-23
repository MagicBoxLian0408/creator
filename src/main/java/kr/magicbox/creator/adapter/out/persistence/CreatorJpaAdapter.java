package kr.magicbox.creator.adapter.out.persistence;

import kr.magicbox.creator.adapter.out.persistence.entity.CreatorEntity;
import kr.magicbox.creator.adapter.out.persistence.mapper.CreatorMapper;
import kr.magicbox.creator.adapter.out.persistence.repository.CreatorJpaRepository;
import kr.magicbox.creator.application.port.out.CreatorRepositoryPort;
import kr.magicbox.creator.domain.aggregate.Creator;
import kr.magicbox.creator.domain.vo.CreatorId;
import kr.magicbox.creator.domain.vo.Nickname;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreatorJpaAdapter implements CreatorRepositoryPort {

    private final CreatorJpaRepository creatorJpaRepository;
    private final CreatorMapper creatorMapper;

    @Override
    public void save(Creator creator) {
        CreatorEntity entity = creatorMapper.toEntity(creator);
        creatorJpaRepository.save(entity);
    }

    @Override
    public void update(Creator creator) {
        creatorJpaRepository.findById(creator.getId().value())
                .ifPresent(entity -> {
                    creatorMapper.updateEntity(creator, entity);
                    creatorJpaRepository.save(entity);
                });
    }

    @Override
    public Optional<Creator> findById(Long id) {
        return creatorJpaRepository.findById(id)
                .map(creatorMapper::toDomain);
    }

    @Override
    public Optional<Creator> findByUserId(UserId userId) {
        return creatorJpaRepository.findByUserId(userId.value())
                .map(creatorMapper::toDomain);
    }

    @Override
    public boolean existsByUserId(UserId userId) {
        return creatorJpaRepository.existsByUserId(userId.value());
    }

    @Override
    public Optional<Creator> findByCreatorId(CreatorId creatorId) {
        return creatorJpaRepository.findById(creatorId.value())
                .map(creatorMapper::toDomain);
    }

    @Override
    public Optional<Creator> findByNickname(Nickname nickname) {
        return creatorJpaRepository.findByNickname(nickname.value())
                .map(creatorMapper::toDomain);
    }

    @Override
    public List<Creator> findAllByCursor(Long cursorId, int size) {
        return creatorJpaRepository.findAllByCursor(cursorId, PageRequest.of(0, size))
                .stream()
                .map(creatorMapper::toDomain)
                .toList();
    }

    @Override
    public List<Creator> searchByNickname(String keyword, Long cursorId, int size) {
        return creatorJpaRepository.searchByNickname(keyword, cursorId, PageRequest.of(0, size))
                .stream()
                .map(creatorMapper::toDomain)
                .toList();
    }
}
