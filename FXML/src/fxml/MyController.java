package fxml;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MyController implements Initializable, ControlScreen {
    ScreensController myController;
    Model model = new Model();
    @FXML private TableView<Student> tableView;
    @FXML private TableColumn<Student, String> markColumn;
    @FXML private TableColumn<Student, String> firstNameColumn;
    @FXML private TableColumn<Student, String> lastNameColumn;
    @FXML private TableColumn<Student, String> ageColumn;
    @FXML private BarChart barChart;
    @FXML private TextArea textArea;
    @FXML private TextField markInput;
    @FXML private TextField firstNameInput;
    @FXML private TextField lastNameInput;
    @FXML private TextField ageInput;
    private final String adress = "D:\\students.txt";
       
    @Override
    public void setParentScreen(ScreensController screenParent){
        myController = screenParent;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        /*PauseTransition delay = new PauseTransition(Duration.millis(1000));
    delay.setOnFinished(event -> {
        model.loadData(new File(adress));
        tableView.getItems().setAll(model.getStudentList());
        model.loadData(new File(adress));
        ObservableList<Student> studentsList = model.getStudentList();
        for(Student s : studentsList){
            tableView.getItems().add(s);
            });
    delay.play();*/
       
        /*
        Crawler crawler = new Crawler();
        crawler.addNewStudentListener(new GUILogger()); 
        crawler.addRemoveStudentListener(new GUILogger()); 
        crawler.addUnchangedListener(new GUILogger());
        crawler.addIterationStartedListener(new GUILogger());
        crawler.addIterationComplitedListener(new GUILogger());
        
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                crawler.run();
               return null;
            }
        };
    Thread th = new Thread(task);
    th.setDaemon(true);
       th.start();*/
    }   
    
    public void initModel(Model model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model ;
}

    @FXML 
    public void handleLogOut(){
        myController.setScreen(MyApplication.screenLogIn);
    }
    
    @FXML 
    public void handleClose(){
        MyApplication.closeProgram();
    }
    
    @FXML 
    public void handleAbout(){
        AboutBox.display();
    }
    
    @FXML 
    public void handleAddButton(){
        Student s = new Student(Double.parseDouble(markInput.getText()),
            firstNameInput.getText(),
            lastNameInput.getText(),
            Integer.parseInt(ageInput.getText()));
        tableView.getItems().add(s);
        
        File f = new File(adress);
        model.saveData(f,s);
        
        markInput.clear();
        firstNameInput.clear();
        lastNameInput.clear();
        ageInput.clear();  
    }
    
    @FXML 
    public void handleDeleteButton(){
        ObservableList<Student> selected, allStudents;
        
        allStudents = tableView.getItems();
        selected = tableView.getSelectionModel().getSelectedItems();
        List<Student> st = new ArrayList<>(selected);
        selected.forEach(allStudents::remove);
        
        File inputFile = new File(adress);
        String adress2 = "D:\\students2.txt";
        File tempFile = new File(adress2);
        
        model.deleteData(inputFile, tempFile, st);    
    }
    
    public void handleLoad(){
        markColumn.setCellValueFactory(new PropertyValueFactory("mark")); 
        firstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName"));
        ageColumn.setCellValueFactory(new PropertyValueFactory("age"));
        model.loadData(new File(adress));
        ObservableList<Student> sList = model.getStudentList();
        for(Student s : sList){
            tableView.getItems().add(s);
        }
        
    
    }
    
}
