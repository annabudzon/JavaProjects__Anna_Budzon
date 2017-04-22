package logger;

import controller.MainScreenController;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import model.StudentModel;
import crawler.Crawler.STATUS;
import java.io.PrintWriter;

public class TextLogger implements Logger, Closeable {

    private final String adress;
    private final File file;

    public TextLogger(String adress) {
        this.adress = adress;
        file = new File(this.adress);

        if (file.exists() && !file.isDirectory()) {
            try (PrintWriter writer = new PrintWriter(file);) {
                writer.print("");
            } catch (IOException e) {
                System.out.println("IOException - TextLogger constructor.");
            }
        }
    }

    @Override
    public void log(STATUS status, StudentModel student, MainScreenController control) {
        String newLine = "\r\n";

        try (FileWriter fw = new FileWriter(file, true);) {
            switch (status) {
                case ADDED:
                    fw.write("-----ADDED: " + student + newLine);
                    break;
                case REMOVED:
                    fw.write("-----REMOVED: " + student + newLine);
                    break;
            }
        } catch (IOException e) {
            System.out.println("IOException.");
        }
    }

    @Override
    public void log(STATUS status, int iteration) {
        String newLine = "\r\n";

        try (FileWriter fw = new FileWriter(file, true);) {
            switch (status) {
                case I_STARTED:
                    fw.write("---ITERATION STARTED: " + iteration + newLine);
                    break;
                case I_COMPLETED:
                    fw.write("---ITERATION COMPLETED: " + iteration + newLine);
                    break;
            }
        } catch (IOException e) {
            System.out.println("IOException.");
        }
    }

    @Override
    public void log(STATUS status, MainScreenController control) {
        String newLine = "\r\n";

        try (FileWriter fw = new FileWriter(file, true);) {
            fw.write("-----UNCHANGED-----" + newLine);
        } catch (IOException e) {
            System.out.println("IOException.");
        }
    }

    @Override
    public void close() throws IOException {
    }

}
