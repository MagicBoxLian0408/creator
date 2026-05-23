package kr.magicbox.creator.application.port.out;

import kr.magicbox.creator.domain.aggregate.CreatorCertification;
import kr.magicbox.creator.domain.enums.CreatorCertificationStatus;
import kr.magicbox.creator.domain.vo.CreatorCertificationId;
import kr.magicbox.creator.domain.vo.UserId;

import java.util.List;
import java.util.Optional;

public interface CreatorCertificationRepositoryPort {

    void save(CreatorCertification certification);

    void update(CreatorCertification certification);

    Optional<CreatorCertification> findById(CreatorCertificationId id);

    List<CreatorCertification> findAllByUserId(UserId userId);

    List<CreatorCertification> findAllByUserIdByCursor(UserId userId, Long cursorId, int size);

    List<CreatorCertification> findAllByStatusByCursor(CreatorCertificationStatus status, Long cursorId, int size);

    void deleteById(CreatorCertificationId id);

    boolean existsByUserIdAndStatus(UserId userId, CreatorCertificationStatus status);
}
