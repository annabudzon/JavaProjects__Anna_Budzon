package Crawler;

import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static Logger[] loggers = new Logger[]{ 
      new GUILogger(),
      new ConsoleLogger(), 
      new MailLogger()
    };
    
    Stage window;
    
    public static void main(String[] args) throws IOException, MyException {
        launch(args);
        //Crawler cr = new Crawler();
        //System.out.println("Podaj adres pliku, zawierajacego dane studentow: ");
        //cr.setAdress(getUserInput());
       // cr.run();
        
    }

    public static String getUserInput() {
        Scanner odczyt = new Scanner(System.in);
        return odczyt.nextLine();
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        MyStage.goApp(primaryStage);
    }
}
    

