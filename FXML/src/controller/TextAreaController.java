package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class TextAreaController implements Initializable {

    @FXML
    private TextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void setTextArea(String logg) {
        textArea.appendText(logg);
    }

}
