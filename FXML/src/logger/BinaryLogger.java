package logger;

import controller.MainScreenController;
import java.io.Closeable;
import java.io.IOException;
import model.StudentModel;
import crawler.Crawler.STATUS;
import static crawler.Crawler.STATUS.ADDED;
import static crawler.Crawler.STATUS.REMOVED;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.LoggedStudent;

public class BinaryLogger implements Logger, Closeable {

    private final String adress;
    private final File file;
    private static List<LoggedStudent> loggedStudents;

    public BinaryLogger(String adress) {
        this.adress = adress;
        file = new File(adress);
        loggedStudents = new ArrayList<>();
    }

    @Override
    public void log(STATUS status, StudentModel student, MainScreenController control) {
        LoggedStudent logSt = new LoggedStudent(student, status);
        String newLine = System.getProperty("line.separator");
        int st;
        if (logSt.getStatus() == ADDED) {
            st = 0;
        } else {
            st = 1;
        }
        String data = logSt.getTime() + ";" + st + ";" + logSt.getMark() + ";" + logSt.getFirstName() + ";" + logSt.getLastName() + ";" + logSt.getAge() + newLine;
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file, true))) {
            byte[] line = data.getBytes("UTF-8");
            dataOutputStream.write(line);
        } catch (IOException e) {
            System.out.println("BinaryLogger DataOutputStream - IOException");
        }
        
        /*List<LoggedStudent> trial = getlistStudents(adress);
        for (LoggedStudent l : trial) {
            System.out.println(l);
        }*/
    }

    @Override
    public void log(STATUS status, int iteration) {
    }

    @Override
    public void log(STATUS status, MainScreenController control) {
    }

    @Override
    public void close() throws IOException {
    }

    public List<LoggedStudent> getlistStudents(String adress) {
        String line;
        LoggedStudent student = new LoggedStudent();

        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(adress))) {
            while (dataInputStream.available() > 0) {
                line = dataInputStream.readLine();
                String[] parts = line.split(";");

                if (parts.length == 4) {
                    for (String el : parts) {
                        if (el.isEmpty()) {
                            return null;
                        }
                    }

                    try {
                        student.setTime(Long.parseLong(parts[0]));
                        if ((Integer.parseInt(parts[1])) == 0) {
                            student.setStatus(ADDED);
                        } else {
                            student.setStatus(REMOVED);
                        }
                        student.setMark(Double.parseDouble(parts[2]));
                        student.setFirstName(parts[3]);
                        student.setLastName(parts[4]);
                        student.setAge(Integer.parseInt(parts[5]));
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("BinaryLogger DataInputStream - IOException");
        }
        return loggedStudents;
    }
}
