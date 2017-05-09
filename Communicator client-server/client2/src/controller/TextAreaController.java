package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class TextAreaController implements Initializable {

    @FXML
    private TextArea messageArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setText(String message) {
        this.messageArea.appendText(message);
    }
}
