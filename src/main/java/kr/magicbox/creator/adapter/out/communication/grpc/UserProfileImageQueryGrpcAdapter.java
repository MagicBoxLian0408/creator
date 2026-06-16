package kr.magicbox.creator.adapter.out.communication.grpc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.grpc.ManagedChannel;
import kr.magicbox.creator.adapter.out.communication.ServiceHost;
import kr.magicbox.creator.adapter.out.communication.grpc.exception.UserServiceUnavailableException;
import kr.magicbox.creator.application.port.out.UserProfileImageQueryPort;
import kr.magicbox.creator.domain.vo.UserId;
import kr.magicbox.creator.grpc.user.GetUserProfileImageUrlRequest;
import kr.magicbox.creator.grpc.user.GetUserProfileImageUrlResponse;
import kr.magicbox.creator.grpc.user.UserServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserProfileImageQueryGrpcAdapter implements UserProfileImageQueryPort {
    private final GrpcChannelFactory grpcChannelFactory;

    @Override
    @CircuitBreaker(name = "userService", fallbackMethod = "getProfileImageUrlFallback")
    public String getProfileImageUrl(UserId userId) {
        GetUserProfileImageUrlRequest request = GetUserProfileImageUrlRequest.newBuilder()
                .setUserId(userId.value())
                .build();

        ManagedChannel channel = grpcChannelFactory.createChannel(ServiceHost.USER.getHostName());
        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc
                .newBlockingStub(channel)
                .withDeadlineAfter(2, TimeUnit.SECONDS);
        GetUserProfileImageUrlResponse response = stub.getUserProfileImageUrl(request);

        return response.getProfileImageUrl();
    }

    @SuppressWarnings("unused")
    private String getProfileImageUrlFallback(UserId userId, Throwable throwable) {
        log.warn("유저 서비스 연결 실패");
        throw new UserServiceUnavailableException(throwable);
    }
}
