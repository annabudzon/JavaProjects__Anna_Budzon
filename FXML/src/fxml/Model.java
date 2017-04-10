package fxml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

    private final ObservableList<Student> studentsList = FXCollections.observableArrayList(student -> 
        new Observable[] {student.markProperty(),student.firstNameProperty(), student.lastNameProperty(), student.ageProperty()});

    private final ObjectProperty<Student> currentStudent = new SimpleObjectProperty<>(null);

    public ObjectProperty<Student> currentStudentProperty() {
        return currentStudent ;
    }

    public final Student getCurrentPerson() {
        return currentStudentProperty().get();
    }

    public final void setCurrentPerson(Student student) {
        currentStudentProperty().set(student);
    }

    public ObservableList<Student> getStudentList() {
        return studentsList ;
    }
    
    public void loadData(File file) {
        try {
            List<Student> students = StudentsParser.parse(file);
            for(Student s : students){
                studentsList.add(s);
            }
        } catch (IOException ex){}        
    }
    
    public void saveData(File file, Student s) {
    String newLine = "\n";
        try{
            FileWriter fw = new FileWriter(file,true);
            fw.write(s.getMark() + ";" + s.getFirstName() + ";" + s.getLastName() + ";" + s.getAge() + newLine);
            fw.close();
        }catch(IOException e){}
    }
    
    public void deleteData(File inputFile, File tempFile, List<Student> st) {
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
}
    

