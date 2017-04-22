package logger;

import controller.MainScreenController;
import java.io.Closeable;
import java.io.IOException;
import model.StudentModel;
import crawler.Crawler.STATUS;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.LoggedStudent;

public class SerializedLogger implements Logger, Closeable {

    private final String adress;
    private final File file;
    private boolean ifExists;
    private static List<LoggedStudent> loggedStudents;

    public SerializedLogger(String adress) {
        this.adress = adress;
        file = new File(this.adress);
        ifExists = file.exists();
        loggedStudents = new ArrayList<>();
    }

    @Override
    public void log(STATUS status, StudentModel student, MainScreenController control) {
        LoggedStudent logSt = new LoggedStudent(student, status);
        //List<LoggedStudent> previousLogged = new ArrayList<>();

        //if (!ifExists) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.reset();
            outputStream.writeObject(logSt);

        } catch (IOException e) {
            System.out.println("SerializedLogger logs 1: IOException.");
        }
        /*  } else {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("test", true)) {
                @Override
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };) {
                outputStream.writeObject(logSt);

            } catch (IOException e) {
                System.out.println("SerializedLogger logs 2: IOException.");
            }
            try (AppendingObjectOutputStream outputStream = new AppendingObjectOutputStream(new FileOutputStream(file,true))) {
                outputStream.writeObject(logSt);
                
            } catch (IOException e) {
                System.out.println("SerializedLogger logs 2: IOException.");
            }
        }*/

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
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(adress))) {
            LoggedStudent l = null;
            while (true) {
                try {
                    l = (LoggedStudent) inputStream.readObject();
                } catch (EOFException e) {
                    if (l != null) {
                        loggedStudents.add(l);
                        break;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("LoggedStudent listStudents() - IOException.");
        } catch (ClassNotFoundException e) {
            System.out.println("LoggedStudent listStudents() - ClassNotFoundException.");
        }

        return loggedStudents;
    }
}
