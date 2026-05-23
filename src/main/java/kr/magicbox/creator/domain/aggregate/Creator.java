package kr.magicbox.creator.domain.aggregate;

import kr.magicbox.creator.domain.constants.CreatorPolicyConstants;
import kr.magicbox.creator.domain.enums.CreatorStatus;
import kr.magicbox.creator.domain.enums.MagicGenre;
import kr.magicbox.creator.domain.exception.CreatorAlreadyBannedException;
import kr.magicbox.creator.domain.exception.CreatorNotBannedException;
import kr.magicbox.creator.domain.exception.InvalidFieldException;
import kr.magicbox.creator.domain.vo.CreatorId;
import kr.magicbox.creator.domain.vo.Nickname;
import kr.magicbox.creator.domain.vo.UserId;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
public class Creator {

    private final CreatorId id;
    private final UserId userId;
    private Nickname nickname;
    private String tagline;
    private String profileImageUrl;
    private String introduction;
    private Set<MagicGenre> genres;
    private CreatorStatus status;

    @Builder(builderMethodName = "createBuilder", builderClassName = "CreateBuilder")
    public Creator(UserId userId, Nickname nickname, String tagline,
                   String profileImageUrl, String introduction, Set<MagicGenre> genres) {
        validateFields(userId, nickname);
        this.id = null;
        this.userId = userId;
        this.nickname = nickname;
        this.tagline = tagline;
        this.profileImageUrl = profileImageUrl;
        this.introduction = introduction;
        this.genres = genres;
        this.status = CreatorStatus.ACTIVE;
    }

    @Builder(builderMethodName = "reconstructBuilder", builderClassName = "ReconstructBuilder")
    public Creator(CreatorId id, UserId userId, Nickname nickname, String tagline,
                   String profileImageUrl, String introduction, Set<MagicGenre> genres,
                   CreatorStatus status) {
        if (id == null) throw new InvalidFieldException("크리에이터 ID는 필수 값입니다.");
        if (status == null) throw new InvalidFieldException("상태는 필수 값입니다.");
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.tagline = tagline;
        this.profileImageUrl = profileImageUrl;
        this.introduction = introduction;
        this.genres = genres;
        this.status = status;
    }

    public Long getUserIdValue() {
        return this.userId.value();
    }

    public String getNicknameValue() {
        return this.nickname.value();
    }

    public boolean isBanned() {
        return this.status == CreatorStatus.BANNED;
    }

    public void updateProfile(Nickname nickname, String tagline, String profileImageUrl,
                              String introduction, Set<MagicGenre> genres) {
        if (nickname != null) {
            this.nickname = nickname;
        }
        if (tagline != null && !tagline.trim().isEmpty()) {
            if (tagline.length() > CreatorPolicyConstants.TAGLINE_MAX_LENGTH) {
                throw new InvalidFieldException("한줄 소개는 " + CreatorPolicyConstants.TAGLINE_MAX_LENGTH + "자 이내여야 합니다.");
            }
            this.tagline = tagline;
        }
        if (profileImageUrl != null && !profileImageUrl.trim().isEmpty()) {
            this.profileImageUrl = profileImageUrl;
        }
        if (introduction != null && !introduction.trim().isEmpty()) {
            if (introduction.length() > CreatorPolicyConstants.INTRODUCTION_MAX_LENGTH) {
                throw new InvalidFieldException("자기소개는 " + CreatorPolicyConstants.INTRODUCTION_MAX_LENGTH + "자 이내여야 합니다.");
            }
            this.introduction = introduction;
        }
        if (genres != null && !genres.isEmpty()) {
            this.genres = genres;
        }
    }

    public void ban() {
        if (this.status == CreatorStatus.BANNED) {
            throw new CreatorAlreadyBannedException();
        }
        this.status = CreatorStatus.BANNED;
    }

    public void unban() {
        if (this.status != CreatorStatus.BANNED) throw new CreatorNotBannedException();
        this.status = CreatorStatus.ACTIVE;
    }

    public void delete() {
        this.status = CreatorStatus.DELETED;
    }

    private void validateFields(UserId userId, Nickname nickname) {
        if (userId == null) {
            throw new InvalidFieldException("사용자 ID는 필수 값입니다.");
        }
        if (nickname == null) {
            throw new InvalidFieldException("닉네임은 필수 값입니다.");
        }
    }
}
