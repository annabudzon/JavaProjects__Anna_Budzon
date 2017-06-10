package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class MessageBoxController implements Initializable {

    @FXML
    TextArea messageArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setText(String message) {
        this.messageArea.appendText(message);
    }

    public void refresh() {
        messageArea.clear();
    }
}
