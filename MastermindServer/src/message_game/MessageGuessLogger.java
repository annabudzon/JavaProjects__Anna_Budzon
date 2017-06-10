package message_game;

import controller.GameController;
import static message_game.MessageState.*;
import boxes.AlertBox;
import javafx.application.Platform;

public class MessageGuessLogger {

    private GameController controller;

    public MessageGuessLogger(GameController controller) {
        this.controller = controller;
    }

    public void log(MessageColors msg) {

        switch (msg.getState()) {
            case GUESS:
                controller.setConnected();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        controller.setGuess(msg);
                    }
                });
                break;
            case GAMEWIN:
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        controller.setDisconnected();
                        controller.setGameWin();
                    }
                });

                break;
            case CONNECTED:
                controller.setConnected();
                break;
            case LOGOUT:
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        controller.setDisconnected();
                    }
                });
                AlertBox box = new AlertBox();
                box.display("Client has logged out. \n");
                break;
            default:
                break;
        }
    }
}
