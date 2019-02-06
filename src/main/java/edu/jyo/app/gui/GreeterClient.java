package edu.jyo.app.gui;

import edu.jyo.app.client.GreeterServiceClient;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.logging.Logger;

public class GreeterClient extends Application {

    private static final Logger logger = Logger.getLogger(GreeterClient.class.getName());

    private final GreeterServiceClient client;
    Button sendButton;
    TextField input;
    TextArea textArea;
    StringBuilder textInTextArea;
    Stage window;
    private EventHandler<ActionEvent> sendButtonEventHandler;

    public GreeterClient() {
        client = new GreeterServiceClient();
        textInTextArea = new StringBuilder();
        sendButtonEventHandler = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (event.getSource() == sendButton) {
                    String text = input.getText();
                    logger.info("Sending message with text " + text);
                    String response = client.sayHello(text);
                    textInTextArea.append(response);
                    textInTextArea.append("\n");
                    textArea.setText(textInTextArea.toString());
                }
            }
        };
    }

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        logger.info("Launching JavaFX client");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        window.setTitle("Greeter Application");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.setVgap(8);
        pane.setHgap(10);

        input = new TextField();
        input.setPromptText("Enter name");
        GridPane.setConstraints(input, 0, 1);

        sendButton = new Button();
        sendButton.setText("send");
        sendButton.setOnAction(sendButtonEventHandler);
        GridPane.setConstraints(sendButton, 1, 1);

        textArea = new TextArea();
        textArea.setPromptText("messages will enter here");
        textArea.setEditable(false);
        GridPane.setConstraints(textArea, 0, 0, 2, 1);

        pane.getChildren().addAll(sendButton, input, textArea);

        Scene scene = new Scene(pane, 300, 250);
        window.setScene(scene);
        window.show();

        window.setOnCloseRequest(e -> close(e));
    }

    private void close(WindowEvent event) {
        event.consume();
        try {
            client.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.close();
    }
}
