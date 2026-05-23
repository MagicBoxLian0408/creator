package kr.magicbox.creator.adapter.out.persistence.mapper;

import kr.magicbox.creator.adapter.out.persistence.entity.CreatorEntity;
import kr.magicbox.creator.domain.aggregate.Creator;
import kr.magicbox.creator.domain.vo.CreatorId;
import kr.magicbox.creator.domain.vo.Nickname;
import kr.magicbox.creator.domain.vo.UserId;
import org.springframework.stereotype.Component;

@Component
public class CreatorMapper {

    public CreatorEntity toEntity(Creator domain) {
        return CreatorEntity.builder()
                .userId(domain.getUserIdValue())
                .nickname(domain.getNicknameValue())
                .tagline(domain.getTagline())
                .profileImageUrl(domain.getProfileImageUrl())
                .introduction(domain.getIntroduction())
                .status(domain.getStatus())
                .genres(domain.getGenres())
                .build();
    }

    public Creator toDomain(CreatorEntity entity) {
        return Creator.reconstructBuilder()
                .id(CreatorId.of(entity.getId()))
                .userId(UserId.of(entity.getUserId()))
                .nickname(Nickname.of(entity.getNickname()))
                .tagline(entity.getTagline())
                .profileImageUrl(entity.getProfileImageUrl())
                .introduction(entity.getIntroduction())
                .status(entity.getStatus())
                .genres(entity.getGenres())
                .build();
    }

    public void updateEntity(Creator creator, CreatorEntity creatorEntity) {
        creatorEntity.updateFromDomain(creator);
    }
}
