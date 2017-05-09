package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import model.UserModel;

public class TableViewController implements Initializable {

    @FXML
    private TableView tableView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void addUser(UserModel user) {
        ObservableList<UserModel> allStudents = tableView.getItems();
        if (!allStudents.contains(user)) {
            for (UserModel u : allStudents) {
                if (u.getUserName().equals(user.getUserName()) || user.getUserName().equals("")) {
                    return;
                }
            }
            tableView.getItems().add(user);
        }
    }

    public void deleteStudent(String userName) {
        ObservableList<UserModel> allStudents = tableView.getItems();
        ObservableList<UserModel> remove = FXCollections.observableArrayList();
        for (UserModel u : allStudents) {
            if (u.getUserName().equals(userName)) {
                remove.add(u);
                remove.forEach(allStudents::remove);
            }
        }
    }

    public void removeUser(String userName) {
        if (tableView.getItems().contains(userName)) {
            tableView.getItems().remove(userName);
        }
    }
}
