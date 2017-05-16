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
    public void log(STATUS status, StudentModel student) {
        LoggedStudent logSt = new LoggedStudent(student, status);
        int st;
        if (logSt.getStatus() == ADDED) {
            st = 0;
        } else {
            st = 1;
        }

        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file, true))) {
            byte[] name = logSt.getFirstName().getBytes("UTF-8");
            byte[] lastName = logSt.getLastName().getBytes("UTF-8");

            dataOutputStream.writeLong(logSt.getTime());
            dataOutputStream.writeInt(st);
            dataOutputStream.writeDouble(logSt.getMark());
            dataOutputStream.write(name);
            dataOutputStream.write(';');
            dataOutputStream.write(lastName);
            dataOutputStream.write(';');
            dataOutputStream.writeInt(logSt.getAge());
            dataOutputStream.writeChar('\n');
        } catch (IOException e) {
            System.out.println("BinaryLogger DataOutputStream - IOException");
        }

        List<LoggedStudent> trial = getlistStudents(adress);
        for (LoggedStudent l : trial) {
            System.out.println(l);
        }
    }

    @Override
    public void log(STATUS status, int iteration) {
    }

    @Override
    public void log(STATUS status) {
    }

    @Override
    public void close() throws IOException {
    }

    public List<LoggedStudent> getlistStudents(String adress) {
        String line;
        LoggedStudent student = new LoggedStudent();
        char lineSep = System.getProperty("line.separator").charAt(0);

        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(adress))) {
            while (dataInputStream.available() > 0) {

                student.setTime(dataInputStream.readLong());
                if (dataInputStream.readInt() == 0) {
                    student.setStatus(ADDED);
                } else {
                    student.setStatus(REMOVED);
                }

                student.setMark(dataInputStream.readDouble());

                StringBuilder name = new StringBuilder();
                char ch;
                int i = 0;
                while ((ch = (char) dataInputStream.readByte()) != ';') {
                    name.append(ch);
                    i++;
                }

                student.setFirstName(name.toString());

                StringBuffer lastName = new StringBuffer();
                while ((ch = (char) dataInputStream.readByte()) != ';') {
                    lastName.append(ch);
                }

                student.setLastName(lastName.toString());
                student.setAge(dataInputStream.readInt());
                if (dataInputStream.readChar() == lineSep) {
                    System.out.println("End of line.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("BinaryLogger DataInputStream - IOException");
        }
        loggedStudents.add(student);
        return loggedStudents;
    }
}
