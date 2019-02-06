package edu.jyo.app.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class ChatClient extends Application {

    private static final Logger logger = Logger.getLogger(ChatClient.class.getName());

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        logger.info("Launching JavaFX client");
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chat Application");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.setVgap(8);
        pane.setHgap(10);

        TextField input = new TextField();
        input.setPromptText("Enter message");
        GridPane.setConstraints(input, 0, 1);

        Button sendButton = new Button();
        sendButton.setText("send");
        GridPane.setConstraints(sendButton, 1, 1);

        TextArea textArea = new TextArea();
        textArea.setPromptText("chat will enter here");
        textArea.setEditable(false);
        GridPane.setConstraints(textArea, 0, 0, 2, 1);

        pane.getChildren().addAll(sendButton, input, textArea);

        Scene scene = new Scene(pane, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
