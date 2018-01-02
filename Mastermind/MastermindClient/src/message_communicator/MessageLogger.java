package message_communicator;

import controller.MainScreenController;
import static message_communicator.MessageType.*;

public class MessageLogger {

    private MainScreenController controller;

    public MessageLogger(MainScreenController controller) {
        this.controller = controller;
    }

    public void log(MessageModel msg) {

        switch (msg.getType()) {
            case CONNECTING:
                controller.setText("---- CONNECTING: " + msg.getMessage());
                break;
            case CONNECTED:
                controller.setText("\nCONNECTED: " + msg.getUserName() + msg.getMessage());
                controller.setConnected();
                break;
            case LOGOUT:
                controller.setText("\nLOGOUT: " + msg.getUserName() + msg.getMessage());
                controller.setDisconnected();
                controller.setLoggedOut();
                break;
            case MESSAGE:
                controller.setText("\n" + msg.getUserName() + ": " + msg.getMessage());
                break;
            case ERROR:
                controller.setText("\nERROR: " + msg.getUserName() + " ---- " + msg.getMessage());
                break;
            default:
                break;
        }
    }
}
