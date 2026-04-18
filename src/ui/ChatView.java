package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatView extends Application {

    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    private TextArea chatArea;
    private TextField inputField;
    private PrintWriter writer;

    @Override
    public void start(Stage stage) {

        chatArea = new TextArea();
        chatArea.setEditable(false);

        inputField = new TextField();
        Button sendButton = new Button("Send");

        HBox bottom = new HBox(10, inputField, sendButton);

        BorderPane root = new BorderPane();
        root.setCenter(chatArea);
        root.setBottom(bottom);

        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Chat");
        stage.show();

        connectToServer();

        sendButton.setOnAction(e -> sendMessage());
        inputField.setOnAction(e -> sendMessage());
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket(HOST, PORT);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            writer = new PrintWriter(socket.getOutputStream(), true);

            // username
            String prompt = reader.readLine();
            Platform.runLater(() -> chatArea.appendText(prompt + "\n"));

            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Enter your username");
            String username = dialog.showAndWait().orElse("Anonymous");

            writer.println(username);

            // поток чтения
            new Thread(() -> {
                String message;
                try {
                    while ((message = reader.readLine()) != null) {
                        String finalMessage = message;
                        Platform.runLater(() ->
                                chatArea.appendText(finalMessage + "\n"));
                    }
                } catch (IOException e) {
                    Platform.runLater(() ->
                            chatArea.appendText("Disconnected.\n"));
                }
            }).start();

        } catch (IOException e) {
            chatArea.appendText("Connection failed.\n");
        }
    }

    private void sendMessage() {
        String message = inputField.getText();

        if (!message.isEmpty()) {
            writer.println(message);
            inputField.clear();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
