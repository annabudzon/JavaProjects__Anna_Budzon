package controller;

import dao.CoachDao;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CoachModel;
import model.TrainingModel;

public class CoachesTabController implements Initializable {

    @FXML
    private TableView coachesTable;

    @FXML
    private TableColumn idColumn;

    @FXML
    private TableColumn firstNameColumn;

    @FXML
    private TableColumn lastNameColumn;

    @FXML
    private TableColumn trainingsColumn;
    
    private List<CoachModel> coaches;

    private CoachDao coachDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurateTable();
        this.coachDao = new CoachDao();
    }

    private void configurateTable() {
        PropertyValueFactory<CoachModel, Integer> idColFactory = new PropertyValueFactory<>("id");
        PropertyValueFactory<CoachModel, String> nameColFactory = new PropertyValueFactory<>("firstName");
        PropertyValueFactory<CoachModel, String> lastNameColFactory = new PropertyValueFactory<>("lastName");
        PropertyValueFactory<CoachModel, List<TrainingModel>> trainingsColFactory = new PropertyValueFactory<>("trainings");

        idColumn.setCellValueFactory(idColFactory);
        firstNameColumn.setCellValueFactory(nameColFactory);
        lastNameColumn.setCellValueFactory(lastNameColFactory);
        trainingsColumn.setCellValueFactory(trainingsColFactory);
    }

    public TableView getTable(){
        return coachesTable;
    }
    
    public List<CoachModel> getAll(){
        return this.coaches;    
    }
    
    public void loadCoaches(CoachDao dao) {
        this.coachDao = dao;
        this.coachDao.setSession();
        this.coaches = new ArrayList<>(this.coachDao.findAll());
        this.coachDao.closeSession();

        coachesTable.getItems().setAll(this.coaches);
    }

    public void addCoach(CoachModel coach) {

        if (!coachesTable.getItems().contains(coach)) {
            coachesTable.getItems().add(coach);
            this.coaches.add(coach);
            coachDao.setSessionWithTransaction();
            coachDao.create(coach);
            coachDao.closeSessionWithTransaction();
        }
    }

    public ObservableList<CoachModel> deleteSelectedCoach() {

        ObservableList<CoachModel> selected, allStudents;

        allStudents = coachesTable.getItems();
        selected = coachesTable.getSelectionModel().getSelectedItems();
        //List<CoachModel> st = new ArrayList<>(selected);
        selected.forEach(allStudents::remove);
        for (CoachModel c : selected) {
            if(this.coaches.contains(c)){
                this.coaches.remove(c);
            }
            coachDao.setSessionWithTransaction();
            CoachModel wanted = coachDao.findById(c.getId());
            coachDao.delete(wanted);
            coachDao.closeSessionWithTransaction();
        }
        return selected;
    }
    
    public void addTraining(TrainingModel training, CoachModel coach){
        coachDao.setSessionWithTransaction();
        CoachModel wanted = coachDao.findById(coach.getId());
        wanted.getTrainings().add(training);
        coachDao.createOrUpdate(wanted);
        coachDao.closeSessionWithTransaction();
        coachesTable.getItems().clear();
    }
    
    public CoachModel find(int id) {
        coachDao.setSession();
        CoachModel coach = coachDao.findById(id);
        coachDao.closeSession();
        
        return coach;
    }
}
