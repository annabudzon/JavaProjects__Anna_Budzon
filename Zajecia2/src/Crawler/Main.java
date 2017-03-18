package Crawler;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main 
{
    
	public static void main( String[] args ) throws IOException, MyException  
	{       
            try {
                Crawler cr = new Crawler();
                System.out.println("Podaj adres pliku, zawierajacego dane studentow: ");
                cr.setAdress(getUserInput());
                cr.run();
            } catch (MessagingException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }     
        }
        
        public static String getUserInput() {
            Scanner odczyt = new Scanner(System.in);
            return odczyt.nextLine();
        } 
}
