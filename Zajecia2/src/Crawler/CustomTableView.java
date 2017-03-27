package Crawler;

import static Crawler.Crawler.adress;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class CustomTableView extends AnchorPane {
    private static TableView<Student> table;
    private static TextField markInput, nameInput, lastNameInput, ageInput;
    private static HBox hBox;
    private static AnchorPane root;
    
    public static TableView display() throws IOException{
        root = new AnchorPane();
        table = new TableView();
        
        //Mark column
        TableColumn<Student,Double> markColumn = new TableColumn("Mark");
        markColumn.setMinWidth(100);
        markColumn.setCellValueFactory(new PropertyValueFactory<>("mark"));
        
        //First name column
        TableColumn<Student,String> nameColumn = new TableColumn("First name");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        
        //Last name column
        TableColumn<Student,String> lastNameColumn = new TableColumn("Last name");
        lastNameColumn.setMinWidth(150);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        
        //Age column
        TableColumn<Student,Integer> ageColumn = new TableColumn("Age");
        ageColumn.setMinWidth(100);
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        
        
        File f = new File(adress);
        List<Student> tempStudents = StudentsParser.parse(f);
        ObservableList<Student> students = FXCollections.observableArrayList(tempStudents);
        table.setItems(students);
        table.getColumns().addAll(markColumn, nameColumn, lastNameColumn, ageColumn);
        return table;
    }
    
    public static AnchorPane createEdit(){
        //Mark input
        markInput = new TextField();
        markInput.setPromptText("Mark");
        markInput.setMinWidth(10);
        
        //First name input
        nameInput = new TextField();
        nameInput.setPromptText("First name");
        nameInput.setMinWidth(10);
        
        //Last name input
        lastNameInput = new TextField();
        lastNameInput.setPromptText("Last name");
        lastNameInput.setMinWidth(10);
        
        //Age input
        ageInput = new TextField();
        ageInput.setPromptText("Age");
        ageInput.setMinWidth(10);
        
        //Add button
        Button addButton = new Button("Add");
        addButton.setOnAction(e-> addStudent());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e-> deleteStudent());
        
        
        hBox = new HBox();
        hBox.setPadding(new Insets(13,5,5,5));
        hBox.setSpacing(5);
        hBox.getChildren().addAll(markInput, nameInput, lastNameInput, ageInput, addButton, deleteButton);
        AnchorPane.setLeftAnchor(hBox,10.0);
        AnchorPane.setRightAnchor(hBox,10.0);
        AnchorPane.setRightAnchor(hBox,10.0);
        root.getChildren().add(hBox);
        return root;
    }
    
    private static void addStudent(){
        Student s = new Student();
        s.setMark(Double.parseDouble(markInput.getText()));
        s.setFirstName(nameInput.getText());
        s.setLastName(lastNameInput.getText());
        s.setAge(Integer.parseInt(ageInput.getText()));
        
        File f = new File(adress);
        try{
            PrintWriter out = new PrintWriter(new FileWriter(f, true));
            out.append("\n");
            out.append(s.getMark() + ";" + s.getFirstName() + ";" + s.getLastName() + ";" + s.getAge());
            out.close();
        }catch(IOException e){
            System.out.println("COULD NOT LOG!!");
        }
        
        table.getItems().add(s);
        markInput.clear();
        nameInput.clear();
        lastNameInput.clear();
        ageInput.clear();
        
    }
    private static void deleteStudent(){
        ObservableList<Student> selected, allStudents;
        
        allStudents = table.getItems();
        selected = table.getSelectionModel().getSelectedItems();
        selected.forEach(allStudents::remove);
        /*
        File f = new File(adress);
        File newf = new File(adress);
        List<Student> resultStudents = new ArrayList<>();
        try {
            resultStudents = StudentsParser.parse(f);
            f.delete();
            PrintWriter f2 = new PrintWriter(newf);
            
            for(Student s : resultStudents){
                 if(!s.equals(selected)){
                    f2.append(s.getMark() + ";" + s.getFirstName() + ";" + s.getLastName() + ";" + s.getAge()+"\n\n");
                }
            }
            f2.close();
        } catch (IOException ex) {
            Logger.getLogger(CustomTableView.class.getName()).log(Level.SEVERE, null, ex);
        
        }*/
    }
}

    

    
