package edu.jyo.app.client;

import edu.jyo.chat.service.protos.ChatServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ChatServiceClient {
    private static final Logger logger = Logger.getLogger(GreeterServiceClient.class.getName());

    private final ManagedChannel channel;
    private final ChatServiceGrpc.ChatServiceBlockingStub blockingStub;

    public ChatServiceClient() {
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress(Constants.LOCALHOST, Constants.PORT).usePlaintext();
        this.channel = channelBuilder.build();
        this.blockingStub = ChatServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}