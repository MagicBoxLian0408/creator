package kr.magicbox.creator.adapter.in.grpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import kr.magicbox.creator.application.dto.query.IsCreatorOwnedByUserQuery;
import kr.magicbox.creator.application.port.in.GetCreatorIdByUserIdUseCase;
import kr.magicbox.creator.application.port.in.GetCreatorNicknameByCreatorIdUseCase;
import kr.magicbox.creator.application.port.in.IsCreatorOwnedByUserUseCase;
import kr.magicbox.creator.domain.exception.CreatorNotFoundException;
import kr.magicbox.creator.domain.vo.CreatorId;
import kr.magicbox.creator.domain.vo.UserId;
import kr.magicbox.creator.grpc.creator.CreatorServiceGrpc;
import kr.magicbox.creator.grpc.creator.GetCreatorIdByUserIdRequest;
import kr.magicbox.creator.grpc.creator.GetCreatorIdByUserIdResponse;
import kr.magicbox.creator.grpc.creator.GetCreatorNicknameByCreatorIdRequest;
import kr.magicbox.creator.grpc.creator.GetCreatorNicknameByCreatorIdResponse;
import kr.magicbox.creator.grpc.creator.IsCreatorOwnedByUserRequest;
import kr.magicbox.creator.grpc.creator.IsCreatorOwnedByUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class CreatorGrpcService extends CreatorServiceGrpc.CreatorServiceImplBase {
    private final IsCreatorOwnedByUserUseCase isCreatorOwnedByUserUseCase;
    private final GetCreatorIdByUserIdUseCase getCreatorIdByUserIdUseCase;
    private final GetCreatorNicknameByCreatorIdUseCase getCreatorNicknameByCreatorIdUseCase;

    @Override
    public void isCreatorOwnedByUser(IsCreatorOwnedByUserRequest request,
                                     StreamObserver<IsCreatorOwnedByUserResponse> responseObserver) {
        boolean ownedByUser = isCreatorOwnedByUserUseCase.isOwnedByUser(
                IsCreatorOwnedByUserQuery.of(
                        CreatorId.of(request.getCreatorId()),
                        UserId.of(request.getUserId())
                )
        );

        responseObserver.onNext(IsCreatorOwnedByUserResponse.newBuilder()
                .setOwnedByUser(ownedByUser)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getCreatorIdByUserId(GetCreatorIdByUserIdRequest request,
                                     StreamObserver<GetCreatorIdByUserIdResponse> responseObserver) {
        try {
            CreatorId creatorId = getCreatorIdByUserIdUseCase.getCreatorIdByUserId(
                    UserId.of(request.getUserId())
            );
            responseObserver.onNext(GetCreatorIdByUserIdResponse.newBuilder()
                    .setCreatorId(creatorId.value())
                    .build());
            responseObserver.onCompleted();
        } catch (CreatorNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("Creator not found for userId: " + request.getUserId())
                    .asRuntimeException());
        }
    }

    @Override
    public void getCreatorNicknameByCreatorId(GetCreatorNicknameByCreatorIdRequest request,
                                              StreamObserver<GetCreatorNicknameByCreatorIdResponse> responseObserver) {
        try {
            String nickname = getCreatorNicknameByCreatorIdUseCase.getCreatorNickname(
                    CreatorId.of(request.getCreatorId())
            );
            responseObserver.onNext(GetCreatorNicknameByCreatorIdResponse.newBuilder()
                    .setNickname(nickname)
                    .build());
            responseObserver.onCompleted();
        } catch (CreatorNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("Creator not found for creatorId: " + request.getCreatorId())
                    .asRuntimeException());
        }
    }
}