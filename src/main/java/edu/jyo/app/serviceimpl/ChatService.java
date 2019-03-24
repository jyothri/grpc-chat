package edu.jyo.app.serviceimpl;

import edu.jyo.chat.service.protos.ChatEngineProtos;
import edu.jyo.chat.service.protos.ChatServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {
    private static final Logger logger = Logger.getLogger(ChatService.class.getName());
    private static final AtomicInteger counter = new AtomicInteger();

    @Override
    public StreamObserver<ChatEngineProtos.ChatMessage> chat(
            final StreamObserver<ChatEngineProtos.ChatMessage> responseObserver) {

        return new StreamObserver<ChatEngineProtos.ChatMessage>() {
            public void onNext(ChatEngineProtos.ChatMessage chatRequest) {
                ChatEngineProtos.ChatMessage.Builder builder = ChatEngineProtos.ChatMessage.newBuilder();
                builder.setId(counter.incrementAndGet());
                builder.setName("name");
                builder.setMessage("message");
                responseObserver.onNext(builder.build());
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

    @Override
    public void getRooms(ChatEngineProtos.GetRoomsRequest request,
                         StreamObserver<ChatEngineProtos.ChatRoom> responseObserver) {

    }

    @Override
    public void createRoom(ChatEngineProtos.CreateRoomRequest request,
                           StreamObserver<ChatEngineProtos.ChatRoom> responseObserver) {
    }

}
