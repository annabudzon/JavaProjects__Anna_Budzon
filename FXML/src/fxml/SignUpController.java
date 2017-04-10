package fxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class SignUpController implements Initializable, ControlScreen {
ScreensController myController;
    @FXML private TextField usernameInput;
    @FXML private PasswordField passwordInput;
    @FXML private TextField ageInput;
    @FXML private TextField adressInput;
    @FXML private RadioButton femaleButton;
    @FXML private RadioButton maleButton;
    @FXML private ToggleGroup toggleGroup;
    @FXML private GANDER gander;
    enum GANDER{
    FEMALE,
    MALE
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
    
    @Override
    public void setParentScreen(ScreensController screenParent){
        myController = screenParent;
    }
    public void handleSave(){
    
        femaleButton.setSelected(false);
        maleButton.setSelected(false);
        usernameInput.clear();
        passwordInput.clear();
        ageInput.clear();
        adressInput.clear();  
    }
    public void handleClear(){
        femaleButton.setSelected(false);
        maleButton.setSelected(false);
        usernameInput.clear();
        passwordInput.clear();
        ageInput.clear();
        adressInput.clear();  
    }
    public void handleCancel(){
        myController.setScreen(MyApplication.screenLogIn);
    }
}
