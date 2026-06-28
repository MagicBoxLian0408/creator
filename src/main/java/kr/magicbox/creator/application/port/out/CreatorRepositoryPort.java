package kr.magicbox.creator.application.port.out;

import kr.magicbox.creator.domain.aggregate.Creator;
import kr.magicbox.creator.domain.vo.CreatorId;
import kr.magicbox.creator.domain.vo.Nickname;
import kr.magicbox.creator.domain.vo.UserId;

import java.util.List;
import java.util.Optional;

public interface CreatorRepositoryPort {

    Creator save(Creator creator);
    void update(Creator creator);

    Optional<Creator> findById(Long id);

    Optional<Creator> findByUserId(UserId userId);

    boolean existsByUserId(UserId userId);

    Optional<Creator> findByCreatorId(CreatorId creatorId);

    Optional<Creator> findByNickname(Nickname nickname);

    List<Creator> findAllByCursor(Long cursorId, int size);

    List<Creator> searchByNickname(String keyword, Long cursorId, int size);

    List<Creator> findAllByIds(List<Long> ids);
}
