package edu.jyo.app.serviceimpl;

import edu.jyo.chat.service.protos.ChatEngineProtos;
import edu.jyo.chat.service.protos.ChatServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {
    private static final Logger logger = Logger.getLogger(ChatService.class.getName());
    private static final AtomicInteger counter = new AtomicInteger();
    private static final Collection<ChatEngineProtos.ChatRoom> rooms = new ArrayList<>();
    Collection<ChatEngineProtos.ChatMessage> messages = new ArrayList<>();

    @Override
    public void createRoom(ChatEngineProtos.CreateRoomRequest request,
                           StreamObserver<ChatEngineProtos.ChatRoom> responseObserver) {
        ChatEngineProtos.ChatRoom.Builder chatRoom = ChatEngineProtos.ChatRoom.newBuilder();
        chatRoom.setName(request.getName());
        chatRoom.setId(counter.incrementAndGet());
        chatRoom.setPublic(true);
        chatRoom.setCount(0);
        ChatEngineProtos.ChatRoom newRoom = chatRoom.build();
        rooms.add(newRoom);
        responseObserver.onNext(newRoom);
        responseObserver.onCompleted();
    }

    @Override
    public void getRooms(ChatEngineProtos.GetRoomsRequest request,
                         StreamObserver<ChatEngineProtos.ChatRoom> responseObserver) {
        for (ChatEngineProtos.ChatRoom room : rooms) {
            responseObserver.onNext(room);
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<ChatEngineProtos.ChatMessage> chat(
            final StreamObserver<ChatEngineProtos.ChatMessage> responseObserver) {

        return new StreamObserver<ChatEngineProtos.ChatMessage>() {
            public void onNext(ChatEngineProtos.ChatMessage chatRequest) {
                ChatEngineProtos.ChatMessage.Builder builder = ChatEngineProtos.ChatMessage.newBuilder();
                builder.setId(counter.incrementAndGet());
                builder.setName(chatRequest.getName());
                builder.setMessage(chatRequest.getMessage());
                messages.add(builder.build());
                for (ChatEngineProtos.ChatMessage message : messages) {
                    responseObserver.onNext(message);
                }
            }

            public void onError(Throwable throwable) {
                logger.severe("Encountered error in chat service " + throwable.getMessage());
                throwable.printStackTrace();
            }

            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
