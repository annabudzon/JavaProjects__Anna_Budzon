package controller;

import boxes.AboutBox;
import boxes.AlertBox;
import static controller.LogInController.closeProgram;
import dao.ClientDao;
import dao.CoachDao;
import dao.KindDao;
import dao.PlaceDao;
import dao.TrainingDao;
import main.MyApplication;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.ClientModel;
import model.CoachModel;
import model.KindOfTraining;
import model.Place;
import model.TrainingModel;
import session.HibernateUtil;

public class MainScreenController implements Initializable, ControlScreen {

    private ScreensController myController;

    final BooleanProperty escPressed = new SimpleBooleanProperty(false);

    @FXML
    private TextField clientFirstNameInput;
    @FXML
    private TextField clientLastNameInput;
    @FXML
    private TextField clientResidenceInput;
    @FXML
    private TextField clientAgeInput;
    @FXML
    private TextField coachFirstNameInput;
    @FXML
    private TextField coachLastNameInput;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeInput;
    @FXML
    private ClientsTabController clientsTabControlController;
    @FXML
    private CoachesTabController coachesTabControlController;
    @FXML
    private TrainingTabController trainingTabControlController;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ChoiceBox coachChoice;
    @FXML
    private ChoiceBox placeChoice;
    @FXML
    private ChoiceBox kindChoice;
    @FXML
    private ChoiceBox clientChoice;
    @FXML
    private ChoiceBox trainingChoice;

    private ClientDao clientDao = new ClientDao();

    private CoachDao coachDao = new CoachDao();

    private TrainingDao trainingDao = new TrainingDao();

    private PlaceDao placeDao = new PlaceDao();

    private KindDao kindDao = new KindDao();

    public MainScreenController() {
    }

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        VBox.setVgrow(clientsTabControlController.getTable(), Priority.ALWAYS);
        VBox.setVgrow(coachesTabControlController.getTable(), Priority.ALWAYS);
        VBox.setVgrow(trainingTabControlController.getTable(), Priority.ALWAYS);

        clientDao = new ClientDao();
        coachDao = new CoachDao();
        trainingDao = new TrainingDao();
        placeDao = new PlaceDao();
        kindDao = new KindDao();

        HibernateUtil.configureSessionFactory();
        loadClients();
        loadCoaches();
        loadTrainings();
        setChoiceBoxes();
        setDatePattern();
    }

    @FXML
    public void handleLogOut() {
        myController.setScreen(MyApplication.screenLogIn);
    }

    @FXML
    public void handleClose() {
        MyApplication.closeProgram();
    }

    @FXML
    public void handleAbout() {
        AboutBox.display();
    }

    @FXML
    public void handleAddClientButton() {

        String firstName = clientFirstNameInput.getText();
        String lastName = clientLastNameInput.getText();
        String residence = clientResidenceInput.getText();
        String age = clientAgeInput.getText();
        if (residence.trim().isEmpty() || firstName.trim().isEmpty() || lastName.trim().isEmpty() || age.trim().isEmpty() || !age.matches("\\d*") || Integer.valueOf(age) > 100 || firstName.matches("[0-9]") || firstName.matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+") || lastName.matches("[0-9]") || lastName.matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+") || firstName.length() > 50 || lastName.length() > 50) {
            AlertBox box = new AlertBox();
            box.display("Niepoprawne dane!");
        } else {
            ClientModel client = new ClientModel(firstName, lastName, residence, Integer.parseInt(age));
            this.clientsTabControlController.addClient(client);
            this.addClientToChoiceBox(client);
        }

        clientFirstNameInput.clear();
        clientLastNameInput.clear();
        clientResidenceInput.clear();
        clientAgeInput.clear();

    }

    @FXML
    public void handleDeleteClientButton() {
        ObservableList<ClientModel> selected = this.clientsTabControlController.deleteSelectedClient();
        List<ClientModel> c = new ArrayList<>(selected);
        this.deleteClientFromChoiceBox(c);
    }

    @FXML
    public void handleAddCoachButton() {

        String firstName = coachFirstNameInput.getText();
        String lastName = coachLastNameInput.getText();
        if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || firstName.matches("[0-9]") || firstName.matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+") || lastName.matches("[0-9]") || lastName.matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+") || firstName.length() > 50 || lastName.length() > 50) {
            AlertBox box = new AlertBox();
            box.display("Niepoprawne dane!");
        } else {
            CoachModel coach = new CoachModel(firstName, lastName);
            this.coachesTabControlController.addCoach(coach);
            this.addCoachToChoiceBox(coach);
        }

        coachFirstNameInput.clear();
        coachLastNameInput.clear();
    }

    @FXML
    public void handleDeleteCoachButton() {
        ObservableList<CoachModel> selected = this.coachesTabControlController.deleteSelectedCoach();
        List<CoachModel> c = new ArrayList<>(selected);
        this.deleteCoachFromChoiceBox(c);
    }

    @FXML
    public void handleAddTrainingButton() {

        Date date = Date.valueOf(datePicker.getValue());
        String time = timeInput.getText();
        String parts[] = time.split(":");

        CoachModel c = (CoachModel) coachChoice.getSelectionModel().getSelectedItem();
        Place p = (Place) placeChoice.getSelectionModel().getSelectedItem();
        KindOfTraining k = (KindOfTraining) kindChoice.getSelectionModel().getSelectedItem();

        CoachModel coach = coachesTabControlController.find(c.getId());

        placeDao.setSession();
        kindDao.setSession();
        Place place = placeDao.findById(p.getId());
        KindOfTraining kind = kindDao.findById(k.getId());
        placeDao.closeSession();
        kindDao.closeSession();
        
        if (coach == null || place == null || kind == null || date == null || time.trim().isEmpty() || parts[0] == null || parts[1] == null || parts[2] == null || !parts[0].matches("\\d+") || parts[0].matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+") || !parts[1].matches("\\d+") || parts[1].matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+") || !parts[2].matches("\\d+") || parts[2].matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+")) {
            AlertBox box = new AlertBox();
            box.display("Niepoprawne dane!");
        } else {
            Time t = new Time(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            TrainingModel training = new TrainingModel(date, t, coach, place, kind);
            this.trainingTabControlController.addTraining(training);
            this.coachesTabControlController.addTraining(training, coach);
            loadCoaches();
        }

        timeInput.clear();
    }

    @FXML
    public void handleDeleteTrainingButton() {
        this.trainingTabControlController.deleteSelectedTraining();
    }
    
    @FXML
    public void addParticipantHandle(){
        ClientModel c = (ClientModel) clientChoice.getSelectionModel().getSelectedItem();
        ClientModel client = clientsTabControlController.find(c.getId());
        
        int training_id = (int) trainingChoice.getSelectionModel().getSelectedItem();
        TrainingModel training = trainingTabControlController.find(training_id);
        
        this.trainingTabControlController.addClient(training, client);
        this.clientsTabControlController.addTraining(training, client);
        loadClients();
        loadTrainings();
        
    }

    public void loadClients() {
        this.clientsTabControlController.loadClients(clientDao);
    }

    public void loadCoaches() {
        this.coachesTabControlController.loadCoaches(coachDao);
    }

    public void loadTrainings() {
        this.trainingTabControlController.loadTrainings(trainingDao);
    }

    public void setChoiceBoxes() {
        coachChoice.getItems().addAll(FXCollections.observableArrayList("Coaches: ", new Separator()));
        List<CoachModel> coaches = coachesTabControlController.getAll();
        coachChoice.getItems().addAll(FXCollections.observableArrayList(coaches));

        placeChoice.getItems().addAll(FXCollections.observableArrayList("Places: ", new Separator()));
        placeDao.setSession();
        List<Place> places = placeDao.findAll();
        placeDao.closeSession();
        placeChoice.getItems().addAll(FXCollections.observableArrayList(places));

        kindChoice.getItems().addAll(FXCollections.observableArrayList("Kinds: ", new Separator()));
        kindDao.setSession();
        List<KindOfTraining> kinds = kindDao.findAll();
        kindDao.closeSession();
        kindChoice.getItems().addAll(FXCollections.observableArrayList(kinds));

        clientChoice.getItems().addAll(FXCollections.observableArrayList("Clients: ", new Separator()));
        List<ClientModel> clients = clientsTabControlController.getAll();
        clientChoice.getItems().addAll(FXCollections.observableArrayList(clients));
        
        List<TrainingModel> trainings = trainingTabControlController.getAll();
        List<Integer> ids = new ArrayList<>();
        for(TrainingModel t: trainings){
            if(!ids.contains(t.getId()))
                ids.add(t.getId());
        }
        trainingChoice.getItems().addAll(FXCollections.observableArrayList("Trenings id: ", new Separator()));
        trainingChoice.getItems().addAll(FXCollections.observableArrayList(ids));
    }

    private void addCoachToChoiceBox(CoachModel coach) {
        coachChoice.getItems().add(coach);
    }

    private void deleteCoachFromChoiceBox(List<CoachModel> coach) {
        if (coachChoice.getItems().contains(coach)) {
            coachChoice.getItems().remove(coach);
        }
    }

    private void addClientToChoiceBox(ClientModel client) {
        clientChoice.getItems().add(client);
    }

    private void deleteClientFromChoiceBox(List<ClientModel> client) {
        if (clientChoice.getItems().contains(client)) {
            clientChoice.getItems().remove(client);
        }
    }
    
    public void setDatePattern() {
        String pattern = "yyyy-MM-dd";
        datePicker.setPromptText(pattern.toLowerCase());
        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

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
