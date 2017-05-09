package message;

import controller.ClientScreenController;
import static message.MessageType.*;
import model.UserModel;

public class MessageLogger {

    private ClientScreenController controller;

    public MessageLogger(ClientScreenController controller) {
        this.controller = controller;
    }

    public void log(MessageModel msg) {

        switch (msg.getType()) {
            case CONNECTING:
                controller.setText("---- CONNECTING: " + msg.getMessage());
                break;
            case CONNECTED:
                controller.setText("\nCONNECTED: " + msg.getUserName() + msg.getMessage());
                break;
            case REGISTER:
                controller.setText("\nREGISTERED: " + msg.getUserName() + msg.getMessage());
                break;
            case LOGIN:
                controller.setText("\nLOGIN: " + msg.getUserName()  + msg.getMessage());
                break;
            case LOGOUT:
                controller.setText("\nLOGOUT: " + msg.getUserName() + msg.getMessage());
                controller.removeUser(msg.getUserName());
                break;
            case MESSAGE:
                controller.setText("\n" + msg.getUserName() + ": " + msg.getMessage() + "\n");
                break;
            case ADDED:
                UserModel user = new UserModel(msg.getUserName());
                controller.addUser(user);
                break;
            case REMOVED:
                controller.removeUser(msg.getUserName());
                break;
            case ERROR:
                controller.setText("\nERROR: " + msg.getUserName() + " ---- " + msg.getMessage());
                break;
            default:
                break;
        }
    }
}
