package Crawler;

import static Crawler.Crawler.adress;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
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
    
    public static TableView display(){
        table = new TableView();
        table.setColumnResizePolicy((TableView.ResizeFeatures param) -> true);
        
        //Mark column
        TableColumn<Student,Double> markColumn = new TableColumn("Mark");
        markColumn.setCellValueFactory(new PropertyValueFactory<>("mark"));
        
        //First name column
        TableColumn<Student,String> nameColumn = new TableColumn("First name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        
        //Last name column
        TableColumn<Student,String> lastNameColumn = new TableColumn("Last name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        
        //Age column
        TableColumn<Student,Integer> ageColumn = new TableColumn("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        
        markColumn.prefWidthProperty().bind(table.widthProperty().divide(4)); 
        nameColumn.prefWidthProperty().bind(table.widthProperty().divide(4));
        lastNameColumn.prefWidthProperty().bind(table.widthProperty().divide(4));
        ageColumn.prefWidthProperty().bind(table.widthProperty().divide(4));
        table.getColumns().addAll(markColumn, nameColumn, lastNameColumn, ageColumn);
        
        return table;
    }
    
    public static AnchorPane createEdit(){
        root = new AnchorPane();
        //Mark input
        markInput = new TextField();
        markInput.setPromptText("Mark");
        markInput.setPrefWidth(100);
        
        //First name input
        nameInput = new TextField();
        nameInput.setPromptText("First name");
        nameInput.setPrefWidth(100);
        
        //Last name input
        lastNameInput = new TextField();
        lastNameInput.setPromptText("Last name");
        lastNameInput.setPrefWidth(100);
        
        //Age input
        ageInput = new TextField();
        ageInput.setPromptText("Age");
        ageInput.setPrefWidth(100);
        
        //Add button
        Button addButton = new Button("Add");
        addButton.setOnAction(e-> addStudent());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e-> deleteStudent());
        
        
        hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(5);
        hBox.getChildren().addAll(markInput, nameInput, lastNameInput, ageInput, addButton, deleteButton);
        AnchorPane.setLeftAnchor(hBox,10.0);
        AnchorPane.setRightAnchor(hBox,10.0);
        AnchorPane.setTopAnchor(hBox,30.0);
        root.getChildren().add(hBox);
        return root;
    }
    
    private static void addStudent(){
        Student s = new Student();
        s.setMark(Double.parseDouble(markInput.getText()));
        s.setFirstName(nameInput.getText());
        s.setLastName(lastNameInput.getText());
        s.setAge(Integer.parseInt(ageInput.getText()));
        
        String newLine = "\n";
        File f = new File(adress);
        try{
            FileWriter fw = new FileWriter(adress,true);
            fw.write(s.getMark() + ";" + s.getFirstName() + ";" + s.getLastName() + ";" + s.getAge() + newLine);
            fw.close();
        }catch(IOException e){}
        
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
        List<Student> st = new ArrayList<>(selected);
        selected.forEach(allStudents::remove);
        
        File inputFile = new File(adress);
        String adress2 = "D:\\students2.txt";
        File tempFile = new File(adress2);
        String lineToRemove;
        String currentLine;
        
        BufferedReader reader;
        BufferedWriter writer;
        
        try {           
            reader = new BufferedReader(new FileReader(inputFile));
            writer = new BufferedWriter(new FileWriter(tempFile));
            
        for(Student s : st){
           
            lineToRemove = s.getMark() + ";" + s.getFirstName() + ";" + s.getLastName() + ";" + s.getAge(); 
            
            while((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                if(trimmedLine.contains(lineToRemove)) continue;
                    writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close(); 
            reader.close(); 
            inputFile.delete();
            Files.move(tempFile.toPath(), inputFile.toPath());  
        }
    
        
    } catch (IOException ex) {}

    }      
       
    public static TableView getTableView() {
        return table;
    }
    public static void setTableView(final TableView tableView) {
        table = tableView;
    }
}

    

    
