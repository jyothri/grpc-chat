package edu.jyo.app;

import edu.jyo.app.serviceimpl.ChatService;
import edu.jyo.app.serviceimpl.GreeterService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * A generic gRPC server that serves various services.
 */
public class ApplicationServer {

    private static final Logger logger = Logger.getLogger(ApplicationServer.class.getName());

    private final int port;
    private final Server grpcServer;

    public ApplicationServer(int port) {
        this(ServerBuilder.forPort(port), port);
    }

    /**
     * Create a RouteGuide server using serverBuilder as a base and features as data.
     */
    public ApplicationServer(ServerBuilder<?> serverBuilder, int port) {
        this.port = port;
        this.grpcServer = serverBuilder
                .addService(new GreeterService())
                .addService(new ChatService())
                .build();
    }

    /**
     * Starts the server and blocks.
     * Use this as the last step of the thread as this method never returns.
     */
    public static void main(String[] args) throws Exception {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        ApplicationServer applicationServer = new ApplicationServer(8980);
        applicationServer.start();
        applicationServer.blockUntilShutdown();
    }

    /**
     * Start serving requests.
     */
    public void start() throws IOException {
        grpcServer.start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may has been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                ApplicationServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    /**
     * Stop serving requests and shutdown resources.
     */
    public void stop() {
        if (grpcServer != null) {
            grpcServer.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (grpcServer != null) {
            grpcServer.awaitTermination();
        }
    }

}
