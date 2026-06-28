package kr.magicbox.creator.adapter.out.communication.grpc;

import io.grpc.ManagedChannel;
import kr.magicbox.creator.adapter.out.communication.ServiceHost;
import kr.magicbox.creator.grpc.release.ReleaseServiceGrpc;
import kr.magicbox.creator.grpc.review.ReviewServiceGrpc;
import kr.magicbox.creator.grpc.shortform.ShortformServiceGrpc;
import kr.magicbox.creator.grpc.subscribe.SubscribeServiceGrpc;
import kr.magicbox.creator.grpc.user.UserServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class GrpcConfiguration {

    @Bean
    public ManagedChannel reviewManagedChannel(GrpcChannelFactory grpcChannelFactory) {
        return grpcChannelFactory.createChannel(ServiceHost.REVIEW.getHostName());
    }

    @Bean
    public ReviewServiceGrpc.ReviewServiceFutureStub reviewServiceFutureStub(ManagedChannel reviewManagedChannel) {
        return ReviewServiceGrpc.newFutureStub(reviewManagedChannel);
    }

    @Bean
    public ManagedChannel subscribeManagedChannel(GrpcChannelFactory grpcChannelFactory) {
        return grpcChannelFactory.createChannel(ServiceHost.SUBSCRIBE.getHostName());
    }

    @Bean
    public SubscribeServiceGrpc.SubscribeServiceFutureStub subscribeServiceFutureStub(ManagedChannel subscribeManagedChannel) {
        return SubscribeServiceGrpc.newFutureStub(subscribeManagedChannel);
    }

    @Bean
    public ManagedChannel releaseManagedChannel(GrpcChannelFactory grpcChannelFactory) {
        return grpcChannelFactory.createChannel(ServiceHost.RELEASE.getHostName());
    }

    @Bean
    public ReleaseServiceGrpc.ReleaseServiceFutureStub releaseServiceFutureStub(ManagedChannel releaseManagedChannel) {
        return ReleaseServiceGrpc.newFutureStub(releaseManagedChannel);
    }

    @Bean
    public ManagedChannel userManagedChannel(GrpcChannelFactory grpcChannelFactory) {
        return grpcChannelFactory.createChannel(ServiceHost.USER.getHostName());
    }

    @Bean
    public UserServiceGrpc.UserServiceFutureStub userServiceFutureStub(ManagedChannel userManagedChannel) {
        return UserServiceGrpc.newFutureStub(userManagedChannel);
    }

    @Bean
    public ManagedChannel shortformManagedChannel(GrpcChannelFactory grpcChannelFactory) {
        return grpcChannelFactory.createChannel(ServiceHost.SHORTFORM.getHostName());
    }

    @Bean
    public ShortformServiceGrpc.ShortformServiceFutureStub shortformServiceFutureStub(ManagedChannel shortformManagedChannel) {
        return ShortformServiceGrpc.newFutureStub(shortformManagedChannel);
    }
}
