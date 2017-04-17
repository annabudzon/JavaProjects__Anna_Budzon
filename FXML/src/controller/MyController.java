package controller;

import fxml.ScreensController;
import boxes.AboutBox;
import boxes.AlertBox;
import fxml.Crawler;
import model.Model;
import main.MyApplication;
import model.Student;
import logger.GUILogger;
import logger.ConsoleLogger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class MyController implements Initializable, ControlScreen {
    private ScreensController myController;
    private Model model = new Model();
    @FXML private TextField markInput;
    @FXML private TextField firstNameInput;
    @FXML private TextField lastNameInput;
    @FXML private TextField ageInput;
    @FXML private TableViewController tabViewControlController;
    @FXML private TextAreaController textAreaControlController;
    @FXML private BarChartController barChartControlController;
    private final String adress = "D:\\students.txt";
      
    public MyController(){}
    
    @Override
    public void setParentScreen(ScreensController screenParent){
        myController = screenParent;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        ConsoleLogger console = new ConsoleLogger();
        GUILogger gui = new GUILogger();
        Crawler crawler = new Crawler(this);
        crawler.addNewStudentListener(console); 
        crawler.addRemoveStudentListener(console); 
        crawler.addUnchangedListener(console);
        crawler.addIterationStartedListener(console);
        crawler.addIterationComplitedListener(console);
        crawler.addNewStudentListener(gui); 
        crawler.addRemoveStudentListener(gui); 
        crawler.addUnchangedListener(gui);
        crawler.addIterationStartedListener(gui);
        crawler.addIterationComplitedListener(gui);
        
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                crawler.run();
               return null;
            }
        };
    Thread th = new Thread(task);
    th.setDaemon(true);
    th.start();
    
        /*this.loadTableView();
        this.loadBarChart();
        this.setTextArea("PROBA---------------------");
        PauseTransition delay = new PauseTransition(Duration.millis(1000));
    delay.setOnFinished(event -> {
        model.loadData(new File(adress));
        tableView.getItems().setAll(model.getStudentList());
        model.loadData(new File(adress));
        ObservableList<Student> studentsList = model.getStudentList();
        for(Student s : studentsList){
            tableView.getItems().add(s);
            });
    delay.play();*/   
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
        String mark = markInput.getText();
        String name = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        String age = ageInput.getText();
        if(mark.trim().isEmpty() || name.trim().isEmpty() || lastName.trim().isEmpty() || age.trim().isEmpty()){
            AlertBox.display("Niepoprawne dane!");
        }else{
            Student s = new Student(Double.parseDouble(mark), name, lastName, Integer.parseInt(age));
            this.tabViewControlController.addInputStudent(adress, model, s);
        }
        markInput.clear();
        firstNameInput.clear();
        lastNameInput.clear();
        ageInput.clear();  
        
    }
    
    @FXML 
    public void handleDeleteButton(){
        this.tabViewControlController.deleteSelectedStudent(adress, model);
    }
    
    @FXML
    public void handleLoad(){
        this.loadTableView();
        this.loadBarChart();
        this.setTextArea("PROBA---------------------");
    }
        
    public void loadTableView(){
        this.tabViewControlController.loadTableView(adress, model);
    }
    
    public void setTextArea(String logg){
        this.textAreaControlController.setTextArea(logg);
    }
    
    public void loadBarChart(){
        this.barChartControlController.loadBarChart(adress);
    }
    
    public void addRow(Student student){
        this.tabViewControlController.addStudent(model, student);
    }
    
    public void deleteRow(Student student){
        this.tabViewControlController.deleteStudent(adress, student);
    }
    
}
