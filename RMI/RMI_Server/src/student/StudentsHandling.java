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

public class StudentsHandling {

    private final ObservableList<Student> studentsList = FXCollections.observableArrayList(student
            -> new Observable[]{new SimpleDoubleProperty(student.getMark()), new SimpleStringProperty(student.getFirstName()), new SimpleStringProperty(student.getLastName()), new SimpleIntegerProperty(student.getAge())});

    public ObservableList<Student> getStudentList() {
        return studentsList;
    }

    public void loadData(File file) {
        try {
            List<Student> students = StudentsParser.parse(file);
            for (Student s : students) {
                studentsList.add(s);
            }
        } catch (IOException ex) {
        }
    }

    public void saveData(File file, Student s) {

        String newLine = "\r\n";

        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(s.getMark() + ";" + s.getFirstName() + ";" + s.getLastName() + ";" + s.getAge() + newLine);

        } catch (IOException e) {
            System.out.println("StudentsHandling saveData() - IOException");
        }

    }

    public void deleteData(File inputFile, File tempFile, List<Student> st) {
        String lineToRemove;
        String currentLine;

        BufferedReader reader;
        BufferedWriter writer;

        try {
            reader = new BufferedReader(new FileReader(inputFile));
            writer = new BufferedWriter(new FileWriter(tempFile));

            for (Student s : st) {
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
            System.out.println("StudentsHandling deleteData() - IOException");
        }

    }
}
