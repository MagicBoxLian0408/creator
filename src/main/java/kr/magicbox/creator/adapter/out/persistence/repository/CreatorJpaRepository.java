package kr.magicbox.creator.adapter.out.persistence.repository;

import kr.magicbox.creator.adapter.out.persistence.entity.CreatorEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CreatorJpaRepository extends JpaRepository<CreatorEntity, Long> {

    Optional<CreatorEntity> findByUserId(Long userId);

    Optional<CreatorEntity> findByNickname(String nickname);

    @Query("SELECT c FROM CreatorEntity c WHERE (:cursorId IS NULL OR c.id < :cursorId) ORDER BY c.id DESC")
    List<CreatorEntity> findAllByCursor(@Param("cursorId") Long cursorId, Pageable limit);

    @Query("SELECT c FROM CreatorEntity c WHERE c.nickname LIKE %:keyword% AND (:cursorId IS NULL OR c.id < :cursorId) ORDER BY c.id DESC")
    List<CreatorEntity> searchByNickname(@Param("keyword") String keyword, @Param("cursorId") Long cursorId, Pageable limit);


    @Query("SELECT COUNT(c) > 0 FROM CreatorEntity c WHERE c.userId = :userId")
    boolean existsByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM CreatorEntity c WHERE c.id IN :ids")
    List<CreatorEntity> findAllByIdIn(@Param("ids") List<Long> ids);
}
