package kr.magicbox.creator.adapter.out.persistence.repository;

import kr.magicbox.creator.adapter.out.persistence.entity.CreatorCertificationEntity;
import kr.magicbox.creator.domain.enums.CreatorCertificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreatorCertificationJpaRepository extends JpaRepository<CreatorCertificationEntity, Long> {

    List<CreatorCertificationEntity> findAllByUserId(Long userId);

    @Query("SELECT c FROM CreatorCertificationEntity c WHERE c.userId = :userId AND (:cursorId IS NULL OR c.id > :cursorId) ORDER BY c.id ASC LIMIT :size")
    List<CreatorCertificationEntity> findAllByUserIdWithCursor(@Param("userId") Long userId, @Param("cursorId") Long cursorId, @Param("size") int size);

    @Query("SELECT c FROM CreatorCertificationEntity c WHERE c.status = :status AND (:cursorId IS NULL OR c.id > :cursorId) ORDER BY c.id ASC LIMIT :size")
    List<CreatorCertificationEntity> findAllByStatusWithCursor(
            @Param("status") CreatorCertificationStatus status,
            @Param("cursorId") Long cursorId,
            @Param("size") int size
    );

    @Query("SELECT COUNT(c) > 0 FROM CreatorCertificationEntity c WHERE c.userId = :userId AND c.status = :status")
    boolean existsByUserIdAndStatus(@Param("userId") Long userId, @Param("status") CreatorCertificationStatus status);
}
