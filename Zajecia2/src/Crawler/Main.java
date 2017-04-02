package Crawler;

import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;

public class Main extends Application {
/*
    public static Logger[] loggers = new Logger[]{ 
      new GUILogger(),
      new ConsoleLogger(), 
      //new MailLogger()
    };*/
    
      
    public static void main(String[] args) throws IOException, MyException {
        launch(args); 
    }

    public static String getUserInput() {
        Scanner odczyt = new Scanner(System.in);
        return odczyt.nextLine();
    }
    @Override
    public void start(Stage primaryStage) {
        Crawler crawler = new Crawler();
        crawler.addNewStudentListener(new ConsoleLogger()); 
        crawler.addNewStudentListener(new GUILogger());
        crawler.addRemoveStudentListener(new ConsoleLogger()); 
        crawler.addRemoveStudentListener(new GUILogger()); 
        crawler.addUnchangedListener(new ConsoleLogger());
        crawler.addUnchangedListener(new GUILogger());
        crawler.addIterationStartedListener(new ConsoleLogger());
        crawler.addIterationStartedListener(new GUILogger());
        crawler.addIterationComplitedListener(new ConsoleLogger());
        crawler.addIterationComplitedListener(new GUILogger());
        
        try {
            MyStage.goApp(primaryStage);
        } catch (IOException ex) {}
      
    
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                crawler.run();
               return null;
            }
        };
    Thread th = new Thread(task);
    th.setDaemon(true);
    th.start();
    }
}
    

