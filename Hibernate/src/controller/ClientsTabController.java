package controller;

import dao.ClientDao;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ClientModel;
import model.CoachModel;
import model.TrainingModel;

public class ClientsTabController implements Initializable {

    @FXML
    private TableView clientsTable;

    @FXML
    private TableColumn idColumn;

    @FXML
    private TableColumn firstNameColumn;

    @FXML
    private TableColumn lastNameColumn;

    @FXML
    private TableColumn ageColumn;

    @FXML
    private TableColumn residenceColumn;

    @FXML
    private TableColumn trainingsColumn;

    private ClientDao clientDao;

    private List<ClientModel> clients;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurateTable();
    }

    private void configurateTable() {
        PropertyValueFactory<CoachModel, Integer> idColFactory = new PropertyValueFactory<>("id");
        PropertyValueFactory<CoachModel, String> nameColFactory = new PropertyValueFactory<>("firstName");
        PropertyValueFactory<CoachModel, String> lastNameColFactory = new PropertyValueFactory<>("lastName");
        PropertyValueFactory<CoachModel, Integer> ageColFactory = new PropertyValueFactory<>("age");
        PropertyValueFactory<CoachModel, String> residenceColFactory = new PropertyValueFactory<>("residence");
        PropertyValueFactory<CoachModel, List<TrainingModel>> trainingsColFactory = new PropertyValueFactory<>("trainings");

        idColumn.setCellValueFactory(idColFactory);
        firstNameColumn.setCellValueFactory(nameColFactory);
        lastNameColumn.setCellValueFactory(lastNameColFactory);
        ageColumn.setCellValueFactory(ageColFactory);
        residenceColumn.setCellValueFactory(residenceColFactory);
        trainingsColumn.setCellValueFactory(trainingsColFactory);
    }

    public TableView getTable() {
        return clientsTable;
    }

    public List<ClientModel> getAll() {
        return this.clients;
    }

    public void loadClients(ClientDao dao) {
        this.clientDao = dao;
        this.clientDao.setSession();
        this.clients = new ArrayList<>(this.clientDao.findAll());
        this.clientDao.closeSession();

        clientsTable.getItems().setAll(this.clients);
    }

    public void addClient(ClientModel client) {
        if (!clientsTable.getItems().contains(client)) {
            clientsTable.getItems().add(client);
            this.clients.add(client);
            clientDao.setSessionWithTransaction();
            clientDao.create(client);
            clientDao.closeSessionWithTransaction();
        }
    }

    public ObservableList<ClientModel> deleteSelectedClient() {
        ObservableList<ClientModel> selected, allStudents;

        allStudents = clientsTable.getItems();
        selected = clientsTable.getSelectionModel().getSelectedItems();
        List<ClientModel> st = new ArrayList<>(selected);
        selected.forEach(allStudents::remove);
        for (ClientModel c : selected) {
            if (this.clients.contains(c)) {
                this.clients.remove(c);
            }
            clientDao.setSessionWithTransaction();
            ClientModel wanted = clientDao.findById(c.getId());
            clientDao.delete(wanted);
            clientDao.closeSessionWithTransaction();
        }
        return selected;
    }

    public void addTraining(TrainingModel training, ClientModel client) {
        clientDao.setSessionWithTransaction();
        ClientModel wanted = clientDao.findById(client.getId());
        wanted.getTrainings().add(training);
        //List<TrainingModel> list = wanted.getTrainings();
        //list.add(training);
        //wanted.setTrainings(list);
        clientDao.createOrUpdate(wanted);
        clientDao.closeSessionWithTransaction();
        clientsTable.getItems().clear();
    }

    public ClientModel find(int id) {
        clientDao.setSession();
        ClientModel client = clientDao.findById(id);
        clientDao.closeSession();

        return client;
    }
}
