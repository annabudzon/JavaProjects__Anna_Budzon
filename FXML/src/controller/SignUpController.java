package controller;

import boxes.AlertBox;
import static controller.LogInController.ACCESS.GRANTED;
import static controller.LogInController.ACCESS.NOT_USER;
import static controller.LogInController.ACCESS.WRONG_PASSWORD;
import static controller.LogInController.closeProgram;
import static controller.SignUpController.GENDER.FEMALE;
import static controller.SignUpController.GENDER.MALE;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import main.MyApplication;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import model.UserModel;

public class SignUpController implements Initializable, ControlScreen {

    ScreensController myController;
    private final AlertBox box = new AlertBox();
    final BooleanProperty escPressed = new SimpleBooleanProperty(false);
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private TextField ageInput;
    @FXML
    private TextField adressInput;
    @FXML
    private RadioButton femaleButton;
    @FXML
    private RadioButton maleButton;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private BorderPane borderPane;

    enum GENDER {
        FEMALE,
        MALE
    }
    private GENDER gender = FEMALE;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }
    @FXML
    public void handleFemaleButton(){
        if(gender == MALE){
            gender = FEMALE;
        }
    }
    
    @FXML
    public void handleMaleButton(){
        if(gender == FEMALE){
            gender = MALE;
        }
    }
    
    @FXML
    public void handleSave() {
        UserModel newUser = new UserModel();
        Properties prop = new Properties();
        InputStream input = null;
        OutputStream output = null;
        String result;
        boolean repeated = false;
        int idx = 0;
        String userName = usernameInput.getText();
        String password = passwordInput.getText();
        int age = Integer.parseInt(ageInput.getText());
        String adress = adressInput.getText();
                
        try {
            input = new FileInputStream("userData.properties");
            prop.load(input);

            if (prop.isEmpty()) {
                idx = 1;
            } else {
                idx = (prop.size() / 2) + 1;
            }

            for(int i = 1; i < idx; i++){
                result = prop.getProperty("user"+i, "lack");
                if(result.equals(userName)){
                    repeated = true;
                }
            }
            input.close();

            output = new FileOutputStream("userData.properties");
            
            if(repeated){
                box.display("This username already exists! Choose diffrent one. ");
            } else if (!usernameInput.getText().trim().isEmpty()
                    && !usernameInput.getText().trim().isEmpty()
                    && !passwordInput.getText().trim().isEmpty()
                    && !adressInput.getText().trim().isEmpty()
                    && !ageInput.getText().trim().isEmpty()) {

                newUser.setUserName(userName);
                newUser.setPassword(password);
                newUser.setAge(age);
                newUser.setAdress(adress);

                if (gender == FEMALE) {
                            newUser.setGender("female");
                        } else {
                            newUser.setGender("male");
                        }
                
                prop.setProperty("user" + idx, newUser.getUserName());
                prop.setProperty("user" + idx + "pass", newUser.getPassword());
            } else{
                box.display("Complete your username and password!");
            }
            /*toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                    if (toggleGroup.getSelectedToggle() != null) {

                        if (toggleGroup.getSelectedToggle().getUserData().equals(femaleButton)) {
                            newUser.setGender("female");
                        } else {
                            newUser.setGender("male");
                        }

                    }

                }
            });*/
            prop.store(output, null);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        femaleButton.setSelected(false);
        maleButton.setSelected(false);
        usernameInput.clear();
        passwordInput.clear();
        ageInput.clear();
        adressInput.clear();
        myController.setScreen(MyApplication.screenLogIn);
    }

    @FXML
    public void handleClear() {
        femaleButton.setSelected(false);
        maleButton.setSelected(false);
        usernameInput.clear();
        passwordInput.clear();
        ageInput.clear();
        adressInput.clear();
    }
    
    @FXML
    public void handleCancel() {
        myController.setScreen(MyApplication.screenLogIn);
    }

    public void handleEscPressed() {
        escPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean werePressed, Boolean arePressed) {
                closeProgram();
            }
        });

        borderPane.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    escPressed.set(true);
                }
            }
        });
    }

    public void handleEscReleased() {
        borderPane.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    escPressed.set(false);
                }
            }
        });
    }
    
}
