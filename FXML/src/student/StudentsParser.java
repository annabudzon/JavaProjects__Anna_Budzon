package student;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import model.StudentModel;

public class StudentsParser {

    public static List<StudentModel> parse(File file) throws IOException {
        try (InputStream stream = new FileInputStream(file)) {
            return parse(stream);
        }
    }

    public static List<StudentModel> parse(URL url) throws IOException {
        try (InputStream stream = url.openStream()) {
            return parse(stream);
        }
    }

    public static List<StudentModel> parse(InputStream stream) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(stream)) {
            return parse(reader);
        }
    }

    public static List<StudentModel> parse(InputStreamReader reader) throws IOException {
        List<StudentModel> result = new ArrayList<>();

        try (BufferedReader tmp = new BufferedReader(reader)) {
            while (true) {
                String line = tmp.readLine();

                if (line == null) {
                    break;
                }

                StudentModel student = parseStudent(line);

                if (student == null) {
                    continue;
                }

                result.add(student);

            }
        }

        return result;
    }

    private static StudentModel parseStudent(String line) {
        String[] parts = line.split(";");

        if (parts.length == 4) {
            for (String el : parts) {
                if (el.isEmpty()) {
                    return null;
                }
            }

            try {
                StudentModel student = new StudentModel();

                student.setMark(Double.parseDouble(parts[0]));
                student.setFirstName(parts[1]);
                student.setLastName(parts[2]);
                student.setAge(Integer.parseInt(parts[3]));
                return student;
            } catch (NumberFormatException e) {
                return null;
            }
        }

        return null;
    }
}
