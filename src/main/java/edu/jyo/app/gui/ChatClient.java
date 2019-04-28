package edu.jyo.app.gui;

import edu.jyo.app.client.ChatServiceClient;
import edu.jyo.chat.service.protos.ChatEngineProtos;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class ChatClient extends Application {

    private static final Logger logger = Logger.getLogger(ChatClient.class.getName());
    private static ChatServiceClient client;
    private Stage window;
    private String name;
    private VBox roomList;
    private ChatEngineProtos.ChatRoom currentRoom;

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        logger.info("Launching JavaFX client");
        client = new ChatServiceClient();
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Chat Application");
        Scene scene = getWelcomeScene();
        window.setScene(scene);
        window.show();
    }

    private Scene getChatRoomScene() {
        logger.info("Switching to chat room scene");
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.setVgap(8);
        pane.setHgap(10);

        Label headerLabel = new Label();
        headerLabel.setText("Chatting as user " + name + ". In room " + currentRoom.getName());
        GridPane.setConstraints(headerLabel, 0, 0, 2, 1);

        TextArea textArea = new TextArea();
        textArea.setPromptText("chat will enter here");
        textArea.setEditable(false);
        GridPane.setConstraints(textArea, 0, 1, 2, 1);

        TextField input = new TextField();
        input.setPromptText("Enter message");
        GridPane.setConstraints(input, 0, 2, 1, 1);

        LinkedBlockingQueue<ChatEngineProtos.ChatMessage> linkedBlockingQueue = new LinkedBlockingQueue<>();
        Button sendButton = new Button();
        sendButton.setText("send");
        sendButton.setOnAction(e -> {
            String text = input.getText();
            logger.fine("Sending text " + text);
            ChatEngineProtos.ChatMessage.Builder builder = ChatEngineProtos.ChatMessage.newBuilder();
            builder.setName(name);
            builder.setMessage(text);
            linkedBlockingQueue.offer(builder.build());
            input.clear();
        });
        GridPane.setConstraints(sendButton, 1, 2, 1, 1);


        pane.getChildren().addAll(headerLabel, textArea, input, sendButton);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                client.chat(textArea, linkedBlockingQueue);
            }
        };
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
        return new Scene(pane, 300, 250);
    }

    private Scene getRoomListScene() {
        logger.info("Switching to room list scene");
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.setVgap(8);
        pane.setHgap(10);

        TextField newRoomField = new TextField();
        newRoomField.setPromptText("Enter new room name");
        GridPane.setConstraints(newRoomField, 1, 0);

        Button createRoomButton = new Button();
        createRoomButton.setText("CREATE");
        createRoomButton.setOnAction(e -> {
            String roomName = newRoomField.getText();
            logger.info("Creating room " + roomName);
            client.createRoom(roomName);
            roomList.getChildren().clear();
            roomList.getChildren().addAll(getRoomButtons());
            newRoomField.clear();
        });
        GridPane.setConstraints(createRoomButton, 1, 1);

        roomList = new VBox();
        roomList.getChildren().addAll(getRoomButtons());
        GridPane.setConstraints(roomList, 0, 0, 1, 2);

        pane.getChildren().addAll(createRoomButton, newRoomField, roomList);

        return new Scene(pane, 300, 250);
    }

    private Collection<Button> getRoomButtons() {
        Collection<Button> rooms = new ArrayList<>();
        for (ChatEngineProtos.ChatRoom chatRoom : client.getRooms()) {
            Button room = new Button();
            String name = chatRoom.getName();
            room.setText(name);
            room.setOnAction(e -> {
                logger.info("Joining room " + name);
                currentRoom = chatRoom;
                window.setScene(getChatRoomScene());
            });
            rooms.add(room);
        }
        return rooms;
    }

    private Scene getWelcomeScene() {
        logger.info("Starting WelcomeScene");
        HBox layout = new HBox();
        TextField nameField = new TextField();
        nameField.setPromptText("Enter name");

        Button joinButton = new Button();
        joinButton.setText("JOIN");

        joinButton.setOnAction(event -> {
            name = nameField.getText();
            window.setScene(getRoomListScene());
        });
        layout.getChildren().addAll(nameField, joinButton);

        return new Scene(layout, 300, 250);
    }
}
