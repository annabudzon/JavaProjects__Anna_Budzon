package controller;

import chat.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MessageBoxController implements Initializable {

    @FXML
    TextArea textArea;
    @FXML
    TextField messageField;
    
    private final boolean isServer = true;
    private final NetworkConnection connection = isServer ? createServer() : createClient();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private Server createServer() {
        return new Server(55555, data -> {
            Platform.runLater(() -> {
                textArea.appendText(data.toString() + "\n");
            });
        });
    }

    private Client createClient() {
        return new Client("127.0.0.1", 55555, data -> {
            Platform.runLater(() -> {
                textArea.appendText(data.toString() + "\n");
            });

        });
    }

    public void init() throws Exception {
        connection.startConnection();
    }

    public void stop() throws Exception {
        connection.closeConnection();
    }

    public void sendHandle() {
        String message = isServer ? "Server: " : "Client: ";
            message += messageField.getText();
            messageField.clear();

            textArea.appendText(message + "\n");
            try {
                connection.send(message);
            } catch (Exception e) {

                textArea.appendText("Failed to send\n");
            }
    }
}
