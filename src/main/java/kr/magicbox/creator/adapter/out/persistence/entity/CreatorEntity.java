package kr.magicbox.creator.adapter.out.persistence.entity;

import jakarta.persistence.*;
import kr.magicbox.creator.domain.aggregate.Creator;
import kr.magicbox.creator.domain.constants.CreatorPolicyConstants;
import kr.magicbox.creator.domain.enums.CreatorStatus;
import kr.magicbox.creator.domain.enums.MagicGenre;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "creator")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreatorEntity extends BaseEntity {

    @Version
    private Long version;

    @Column(nullable = false, unique = true, updatable = false)
    private Long userId;

    @Column(nullable = false, length = CreatorPolicyConstants.NICKNAME_MAX_LENGTH)
    private String nickname;

    @Column(length = CreatorPolicyConstants.TAGLINE_MAX_LENGTH)
    private String tagline;

    @Column(columnDefinition = "TEXT")
    private String profileImageUrl;

    @Column(length = CreatorPolicyConstants.INTRODUCTION_MAX_LENGTH)
    private String introduction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CreatorStatus status;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "creator_genre", joinColumns = @JoinColumn(name = "creator_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private final Set<MagicGenre> genres = new HashSet<>();

    @Builder
    public CreatorEntity(Long userId, String nickname, String tagline,
                         String profileImageUrl, String introduction, CreatorStatus status,
                         Set<MagicGenre> genres) {
        this.userId = userId;
        this.nickname = nickname;
        this.tagline = tagline;
        this.profileImageUrl = profileImageUrl;
        this.introduction = introduction;
        this.status = status != null ? status : CreatorStatus.ACTIVE;
        if (genres != null) {
            this.genres.addAll(genres);
        }
    }

    public void updateFromDomain(Creator creator) {
        this.nickname = creator.getNicknameValue();
        this.tagline = creator.getTagline();
        this.profileImageUrl = creator.getProfileImageUrl();
        this.introduction = creator.getIntroduction();
        this.status = creator.getStatus();
        this.genres.clear();
        if (creator.getGenres() != null) {
            this.genres.addAll(creator.getGenres());
        }
    }
}
