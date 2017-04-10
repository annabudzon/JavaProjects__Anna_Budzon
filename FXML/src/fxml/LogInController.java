package fxml;

import static fxml.MyApplication.window;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInController implements Initializable, ControlScreen {
    ScreensController myController;
    
    @FXML TextField usernameInput;
    @FXML PasswordField passwordInput;
    @FXML Label error;
    
    
    @FXML
    protected void handleSignInEvent(){
        error.setText("");
        if (!passwordInput.getText().equals("1234") || !usernameInput.getText().equals("abudzon")) {
            error.setText("Your username or password is incorrect!");
            usernameInput.clear();
            passwordInput.clear();
        
        } else {
            myController.setScreen(MyApplication.screenMain);
            usernameInput.clear();
            passwordInput.clear();
        }
        
    }
    
    @FXML
    protected void handleSignUpEvent(){
        myController.setScreen(MyApplication.screenSignUp);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @Override
    public void setParentScreen(ScreensController screenParent){
        myController = screenParent;
    }
    
    public static void closeProgram(){
        boolean answer = ConfirmBox.display("Closing window", "Sure you want to exit?");
        if(answer)
            window.close();
    }
    
}
