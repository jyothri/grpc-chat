package edu.jyo.app.client;

import edu.jyo.greet.service.protos.GreetEngineProtos;
import edu.jyo.greet.service.protos.GreeterGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class GreeterServiceClient {
    private static final Logger logger = Logger.getLogger(GreeterServiceClient.class.getName());

    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;
    private final GreeterGrpc.GreeterStub asyncStub;
    private final GreeterGrpc.GreeterFutureStub futureStub;

    public GreeterServiceClient() {
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 8980).usePlaintext();
        channel = channelBuilder.build();
        blockingStub = GreeterGrpc.newBlockingStub(channel);
        asyncStub = GreeterGrpc.newStub(channel);
        futureStub = null; //GreeterGrpc.GreeterFutureStub(channelBuilder);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public String sayHello(String name) {
        logger.info("Invoking say hello");
        GreetEngineProtos.GreetRequest.Builder builder = GreetEngineProtos.GreetRequest.newBuilder();
        builder.setId(1);
        builder.setName(name);
        builder.setEmail("john.doe@johndoe.net");
        GreetEngineProtos.GreetResponse response = blockingStub.sayHello(builder.build());
        logger.info("Received response from blockingStub call:" + response.getMessage());
        return response.getMessage();
    }
}
