package Crawler;

import static Crawler.Crawler.ExtremumMode.*;
import static Crawler.Crawler.OrderMode.*;
import static Crawler.Sorter.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
//import javax.mail.MessagingException;


public class Crawler {

private static List<Student> resultStudents;
private static List<Student> previousStudents;
public static String adress = "D:\\students.txt";
static int i = 0;
public enum OrderMode
{
MARK,
FIRST_NAME,
LAST_NAME,
AGE;
}
public enum ExtremumMode
{
MAX,
MIN
}
    
public Crawler() {
    previousStudents = new ArrayList<>();
    //adress = "D:\\Materiały\\SEMESTR 4\\Java - G. Górecki\\Zajęcia 3\\LAB03-scratch\\src\\example\\students.txt";
}
         
public static List<Student> getResults(){
    return resultStudents;
}

public static void print(List<Student> students){ //wyświetlanie
    for(Student s : students){
        System.out.println( s.getMark() + " " + s.getFirstName() + " " + s.getLastName() + " " + s.getAge() );
    } 
}

public static List<Student> extractStudents( List<Student> students, OrderMode mode ){// posortowani studenci 
    List<Student> sortStudents = new ArrayList<>();
    switch(mode){
        case MARK: 
                sortStudents = sortMark(students);
            
            System.out.println("\nPosortowana ze wzgledu na ocenę: \n");
            print(sortStudents);
            break;
        case FIRST_NAME:
            sortStudents = sortName(students);
            System.out.println("\nPosortowana ze wzgledu na imię: \n");
            print(sortStudents);
            break;
        case LAST_NAME:
            sortStudents = sortLastName(students);
            System.out.println("\nPosortowana ze wzgledu na nazwisko: \n");
            print(sortStudents);
            break;
        case AGE: 
            sortStudents = sortAge(students);
            System.out.println("\nPosortowana ze wzgledu na wiek: \n");
            print(sortStudents);
            break;
        default:
            System.out.println("\nNiepoprawny status - nie da się posortować.");
    }
    return sortStudents;
}
public static double extractMark(List<Student> students, ExtremumMode mode ){// maksymalna lub minimalna ocena
    double mark = 0.0;
    switch(mode){
        case MAX:
            mark = maxMark(students);
            break;
        case MIN:
            mark = minMark(students);
            break;
        default:
            System.out.println("\nNiepoprawny status.");
    }
    return mark;
}
public static int extractAge(List<Student> students, ExtremumMode mode ){// maksymalny lub minimalny wiek
    int age = 0;
    switch(mode){
        case MAX:
            age = maxAge(students);
            break;
        case MIN:
            age = minAge(students);
            break;
        default:
            System.out.println("\nNiepoprawny status.");
    }
    
   return age; 
}

public void run() throws IOException, MyException{
    while(true){
        File f = new File(adress);
        if(f == null){
            throw new MyException();
        }
       resultStudents = new ArrayList<>();
       resultStudents = StudentsParser.parse(f);
       i++; //iteracja pętli
       StudentsListener handler;
       
       if(previousStudents == null){ //jeśli i = 1
           if(!resultStudents.isEmpty()){
           handler = new StudentsListener();
           handler.notifyAdded(previousStudents, resultStudents);
           }
       }
       else if(previousStudents.size() > resultStudents.size()){
           //usunięto
           handler = new StudentsListener();
           handler.notifyRemoved(previousStudents, resultStudents);
       }
       else if(previousStudents.size() < resultStudents.size()){
           //dodano
           handler = new StudentsListener();
           handler.notifyAdded(previousStudents, resultStudents);
        }
       else{
            // nie zmodyfikowano  
           handler = new StudentsListener();
           handler.notifyUnchanged();
       }
       
       previousStudents = resultStudents;
       
        try {
            Thread.sleep(10000);
         } catch (Exception e) {
            System.out.println(e);
         }
    }
}

}//class


 

    

