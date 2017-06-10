package controller;

import dao.TrainingDao;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ClientModel;
import model.CoachModel;
import model.KindOfTraining;
import model.Place;
import model.TrainingModel;

public class TrainingTabController implements Initializable {

    @FXML
    private TableView trainingTable;

    @FXML
    private TableColumn idColumn;

    @FXML
    private TableColumn dateColumn;

    @FXML
    private TableColumn timeColumn;

    @FXML
    private TableColumn coachColumn;

    @FXML
    private TableColumn placeColumn;

    @FXML
    private TableColumn kindColumn;

    @FXML
    private TableColumn clientsColumn;

    private TrainingDao trainingDao;

    private List<TrainingModel> trainings;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurateTable();
        this.trainingDao = new TrainingDao();
    }

    private void configurateTable() {
        PropertyValueFactory<TrainingModel, Integer> idColFactory = new PropertyValueFactory<>("id");
        PropertyValueFactory<TrainingModel, Date> dateColFactory = new PropertyValueFactory<>("date");
        PropertyValueFactory<TrainingModel, Time> timeColFactory = new PropertyValueFactory<>("time");
        PropertyValueFactory<TrainingModel, CoachModel> coachColFactory = new PropertyValueFactory<>("coach");
        PropertyValueFactory<TrainingModel, Place> placeColFactory = new PropertyValueFactory<>("place");
        PropertyValueFactory<TrainingModel, KindOfTraining> kindColFactory = new PropertyValueFactory<>("kind");
        PropertyValueFactory<TrainingModel, List<ClientModel>> clientsColFactory = new PropertyValueFactory<>("clients");

        idColumn.setCellValueFactory(idColFactory);
        dateColumn.setCellValueFactory(dateColFactory);
        timeColumn.setCellValueFactory(timeColFactory);
        coachColumn.setCellValueFactory(coachColFactory);
        placeColumn.setCellValueFactory(placeColFactory);
        kindColumn.setCellValueFactory(kindColFactory);
        clientsColumn.setCellValueFactory(clientsColFactory);
    }

    public TableView getTable() {
        return trainingTable;
    }
    
    public List<TrainingModel> getAll() {
        return this.trainings;
    }

    public void loadTrainings(TrainingDao dao) {
        this.trainingDao = dao;
        this.trainingDao.setSession();
        this.trainings = new ArrayList<>(this.trainingDao.findAll());
        this.trainingDao.closeSession();

        trainingTable.getItems().setAll(this.trainings);
    }

    public void addTraining(TrainingModel training) {

        if (!trainingTable.getItems().contains(training)) {
            trainingTable.getItems().add(training);

            this.trainings.add(training);
            trainingDao.setSessionWithTransaction();
            trainingDao.create(training);
            trainingDao.closeSessionWithTransaction();
        }
    }
    
    public void addClient(TrainingModel training, ClientModel client) {
        trainingDao.setSessionWithTransaction();
        TrainingModel wanted = trainingDao.findById(training.getId());
        List<ClientModel> list = wanted.getClients();
        list.add(client);
        wanted.setClients(list);
        trainingDao.update(wanted);
        trainingDao.closeSessionWithTransaction();
        trainingTable.getItems().clear();
    }

    public void deleteSelectedTraining() {

        ObservableList<TrainingModel> selected, allStudents;

        allStudents = trainingTable.getItems();
        selected = trainingTable.getSelectionModel().getSelectedItems();
        //List<TrainingModel> st = new ArrayList<>(selected);
        selected.forEach(allStudents::remove);
        for (TrainingModel t : selected) {
            if (this.trainings.contains(t)) {
                this.trainings.remove(t);
            }
            trainingDao.setSessionWithTransaction();
            TrainingModel wanted = trainingDao.findById(t.getId());
            trainingDao.delete(wanted);
            trainingDao.closeSessionWithTransaction();
        }
    }

    public TrainingModel find(int id) {
        trainingDao.setSession();
        TrainingModel coach = trainingDao.findById(id);
        trainingDao.closeSession();

        return coach;
    }
}
