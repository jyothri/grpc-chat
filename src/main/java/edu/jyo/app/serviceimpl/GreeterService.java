package edu.jyo.app.serviceimpl;

import edu.jyo.greet.service.protos.GreetEngineProtos;
import edu.jyo.greet.service.protos.GreeterGrpc;

import java.util.logging.Logger;

public class GreeterService extends GreeterGrpc.GreeterImplBase {

    private static final Logger logger = Logger.getLogger(GreeterService.class.getName());


    @Override
    public void sayHello(edu.jyo.greet.service.protos.GreetEngineProtos.GreetRequest request,
                         io.grpc.stub.StreamObserver<edu.jyo.greet.service.protos.GreetEngineProtos.GreetResponse> responseObserver) {
        logger.info("Received request for sayHello");
        GreetEngineProtos.GreetResponse.Builder builder = GreetEngineProtos.GreetResponse.newBuilder();
        builder.setMessage(String.format("Hello %s", request.getName()));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
        logger.info("completed service of sayHello");
    }
}
