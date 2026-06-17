package kr.magicbox.creator.application.service;

import kr.magicbox.creator.application.port.in.GetCreatorProfileByCreatorIdUseCase;
import kr.magicbox.creator.application.port.in.GetCreatorProfilesBatchUseCase;
import kr.magicbox.creator.application.port.out.CreatorRepositoryPort;
import kr.magicbox.creator.domain.vo.CreatorId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetCreatorProfilesBatchService implements GetCreatorProfilesBatchUseCase {

    private final CreatorRepositoryPort creatorRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public Map<Long, GetCreatorProfileByCreatorIdUseCase.CreatorProfile> getCreatorProfilesBatch(List<CreatorId> creatorIds) {
        List<Long> ids = creatorIds.stream().map(CreatorId::value).toList();
        return creatorRepositoryPort.findAllByIds(ids).stream()
                .collect(Collectors.toMap(
                        creator -> creator.getId().value(),
                        creator -> new GetCreatorProfileByCreatorIdUseCase.CreatorProfile(
                                creator.getId().value(),
                                creator.getNicknameValue(),
                                creator.getProfileImageUrl()
                        )
                ));
    }
}
