package message;

import controller.ClientScreenController;
import static message.MessageType.*;
import model.UserModel;

public class MessageLogger {

    private ClientScreenController controller;

    public MessageLogger(ClientScreenController controller) {
        this.controller = controller;
    }

    public void log(int type, String message) {

        switch (type) {
            case CONNECTING:
                controller.setText("\nCONNECTING: " + message + "\n");
                break;
            case CONNECTED:
                controller.setText("\nCONNECTED: " + message + "\n");
                break;
            case REGISTER:
                controller.setText("REGISTERED: " + message + "\n");
                break;
            case LOGIN:
                controller.setText("LOGIN: " + message + "\n");
                break;
            case LOGOUT:
                controller.setText("LOGOUT: " + message + "\n");
                controller.removeUser(message);
                break;
            case MESSAGE:
                controller.setText(message);
                break;
            case ADDED:
                UserModel user = new UserModel(message);
                controller.addUser(user);
                break;
            case REMOVED:
                controller.removeUser(message);
                break;
            case ERROR:
                controller.setText("ERROR: " + message);
                break;
            default:
                break;
        }
    }
}
