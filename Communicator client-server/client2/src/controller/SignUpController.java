package controller;

import boxes.AlertBox;
import boxes.ConfirmBox;
import static controller.SignUpController.GENDER.FEMALE;
import static controller.SignUpController.GENDER.MALE;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.MainScreen;
import model.UserModel;

public class SignUpController implements Initializable, ControlScreen {

    private ScreensController myController;

    private final AlertBox box = new AlertBox();

    private final BooleanProperty escPressed = new SimpleBooleanProperty(false);

    public enum GENDER {
        FEMALE,
        MALE
    }

    private GENDER gender = FEMALE;

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
    private BorderPane borderPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }

    @FXML
    public void handleFemaleButton() {
        if (gender == MALE) {
            gender = FEMALE;
        }
    }

    @FXML
    public void handleMaleButton() {
        if (gender == FEMALE) {
            gender = MALE;
        }
    }

    @FXML
    public void handleSave() {
        UserModel newUser = new UserModel();
        Properties prop = new Properties();
        InputStream input;
        OutputStream output = null;
        String result;
        boolean repeated = false;
        int idx;

        try {
            if (!usernameInput.getText().trim().isEmpty()
                    || !usernameInput.getText().trim().isEmpty()
                    || !passwordInput.getText().trim().isEmpty()
                    || !adressInput.getText().trim().isEmpty()
                    || !ageInput.getText().trim().isEmpty()) {

                String userName = usernameInput.getText();
                String password = passwordInput.getText();
                String adress = adressInput.getText();
                int age;
                if(!userName.matches("[0-9]") && ageInput.getText().matches("\\d*") && !userName.matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+")) {
                    age = Integer.parseInt(ageInput.getText());
                    input = new FileInputStream("userData.properties");
                    prop.load(input);

                    if (prop.isEmpty()) {
                        idx = 1;
                    } else {
                        idx = (prop.size() / 2) + 1;
                    }

                    for (int i = 1; i < idx; i++) {
                        result = prop.getProperty("user" + i, "lack");
                        if (result.equals(userName)) {
                            repeated = true;
                        }
                    }

                    if (!repeated) {

                        input.close();

                        output = new FileOutputStream("userData.properties");

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
                        prop.store(output, null);

                        box.display("Registration was completed successfully. You have been registered. ");
                        FXMLLoader loader = myController.setScreen(MainScreen.screenLogIn);
                        LogInController clientControl = loader.getController();
                        clientControl.setRegistered(userName);
                    } else {
                        box.display("This username already exists! Choose diffrent one. ");
                    }
                }

            } else {
                box.display("Complete your username and password!");
            }

        } catch (IOException ex) {
            System.out.println("SignUpController - IOException.");
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    System.out.println("SignUpController output.close() - IOException.");
                }
            }
        }

        femaleButton.setSelected(false);
        maleButton.setSelected(false);
        usernameInput.clear();
        passwordInput.clear();
        ageInput.clear();
        adressInput.clear();
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
        myController.setScreen(MainScreen.screenLogIn);
    }

    public void closeProgram() {
        ConfirmBox confbox = new ConfirmBox();
        boolean answer = confbox.display("Closing window", "Sure you want to exit?");
        if (answer) {
            Stage window = (Stage) borderPane.getScene().getWindow();
            window.close();
        }
    }

    public void handleEscPressed() {
        escPressed.addListener((ObservableValue<? extends Boolean> observable, Boolean werePressed, Boolean arePressed) -> {
            closeProgram();
        });

        borderPane.getScene().setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                escPressed.set(true);
            }
        });
    }

    public void handleEscReleased() {
        borderPane.getScene().setOnKeyReleased((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                escPressed.set(false);
            }
        });
    }

}
