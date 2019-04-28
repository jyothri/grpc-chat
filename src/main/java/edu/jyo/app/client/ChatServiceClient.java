package edu.jyo.app.client;

import edu.jyo.chat.service.protos.ChatEngineProtos;
import edu.jyo.chat.service.protos.ChatServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServiceClient {
    private static final Logger logger = Logger.getLogger(GreeterServiceClient.class.getName());

    private final ManagedChannel channel;
    private final ChatServiceGrpc.ChatServiceBlockingStub blockingStub;
    private final AtomicInteger counter = new AtomicInteger();
    private final ChatServiceGrpc.ChatServiceStub asyncStub;

    public ChatServiceClient() {
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress(Constants.LOCALHOST, Constants.PORT).usePlaintext();
        this.channel = channelBuilder.build();
        this.blockingStub = ChatServiceGrpc.newBlockingStub(channel);
        this.asyncStub = ChatServiceGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public ChatEngineProtos.ChatRoom createRoom(String roomName) {
        ChatEngineProtos.CreateRoomRequest.Builder newRoomBuilder = ChatEngineProtos.CreateRoomRequest.newBuilder();
        newRoomBuilder.setName(roomName);
        newRoomBuilder.setId(counter.incrementAndGet());
        ChatEngineProtos.ChatRoom chatRoom = blockingStub.createRoom(newRoomBuilder.build());
        return chatRoom;
    }

    public Collection<ChatEngineProtos.ChatRoom> getRooms() {
        Collection<ChatEngineProtos.ChatRoom> rooms = new ArrayList<>();
        logger.info("Getting rooms");
        ChatEngineProtos.GetRoomsRequest.Builder getRoomBuilder = ChatEngineProtos.GetRoomsRequest.newBuilder();
        Iterator<ChatEngineProtos.ChatRoom> chatRooms = blockingStub
                .withDeadlineAfter(Constants.deadlineMs, TimeUnit.MILLISECONDS)
                .getRooms(getRoomBuilder.build());
        rooms.clear();
        while (chatRooms.hasNext()) {
            rooms.add(chatRooms.next());
        }
        logger.info("Completed Getting rooms");
        return rooms;
    }

    public void chat(TextArea textArea, LinkedBlockingQueue<ChatEngineProtos.ChatMessage> linkedBlockingQueue) {
        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<ChatEngineProtos.ChatMessage> requestObserver =
                asyncStub.chat(new StreamObserver<ChatEngineProtos.ChatMessage>() {
                    @Override
                    public void onNext(ChatEngineProtos.ChatMessage message) {
                        logger.info("Got message id=" + message.getId()
                                + " name:" + message.getName()
                                + "message:" + message.getMessage());
                        StringBuilder text = new StringBuilder(textArea.getText());
                        text.append(message.getName());
                        text.append(" : ");
                        text.append(message.getMessage());
                        text.append("\n");
                        textArea.setText(text.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Status status = Status.fromThrowable(t);
                        logger.log(Level.WARNING, "RouteChat Failed: {0}", status);
                        finishLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        logger.info("Finished RouteChat");
                        finishLatch.countDown();
                    }
                });

        try {
            while (true) {
                ChatEngineProtos.ChatMessage request = linkedBlockingQueue.take();
                requestObserver.onNext(request);
            }
        } catch (RuntimeException e) {
            // Cancel RPC
            requestObserver.onError(e);
            throw e;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Mark the end of requests
        requestObserver.onCompleted();
    }
}