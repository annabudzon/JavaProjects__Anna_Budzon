package student;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javafx.beans.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.StudentModel;

public class StudentsHandling {

    private final ObservableList<StudentModel> studentsList = FXCollections.observableArrayList(student
            -> new Observable[]{new SimpleDoubleProperty(student.getMark()), new SimpleStringProperty(student.getFirstName()), new SimpleStringProperty(student.getLastName()), new SimpleIntegerProperty(student.getAge())});

    public ObservableList<StudentModel> getStudentList() {
        return studentsList;
    }

    public void loadData(File file) {
        try {
            List<StudentModel> students = StudentsParser.parse(file);
            for (StudentModel s : students) {
                studentsList.add(s);
            }
        } catch (IOException ex) {
        }
    }

    public void saveData(File file, StudentModel s) {
        String newLine = "\r\n";
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write(s.getMark() + ";" + s.getFirstName() + ";" + s.getLastName() + ";" + s.getAge() + newLine);
            fw.close();
        } catch (IOException e) {
        }
    }

    public void deleteData(File inputFile, File tempFile, List<StudentModel> st) {
        String lineToRemove;
        String currentLine;

        BufferedReader reader;
        BufferedWriter writer;

        try {
            reader = new BufferedReader(new FileReader(inputFile));
            writer = new BufferedWriter(new FileWriter(tempFile));

            for (StudentModel s : st) {
                lineToRemove = s.getMark() + ";" + s.getFirstName() + ";" + s.getLastName() + ";" + s.getAge();

                while ((currentLine = reader.readLine()) != null) {
                    String trimmedLine = currentLine.trim();
                    if (trimmedLine.contains(lineToRemove)) {
                        continue;
                    }
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
                writer.close();
                reader.close();
                inputFile.delete();
                Files.move(tempFile.toPath(), inputFile.toPath());
            }
        } catch (IOException ex) {
        }
    }
}
