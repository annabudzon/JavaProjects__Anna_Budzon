package Crawler;

import static Crawler.Crawler.ExtremumMode.*;
import static Crawler.Crawler.OrderMode.*;
import static Crawler.Sorter.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
//import javax.mail.MessagingException;


public class Crawler {
private final Logger[] loggers;
private List<Student> resultStudents;
private List<Student> previousStudents;
private String adress;
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
    
    
public Crawler() throws com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException{
    loggers = new Logger[] { new ConsoleLogger(), new MailLogger()/*, new GUILogger()*/ };
            
    resultStudents = new ArrayList<>();
    previousStudents = new ArrayList<>();
    //adress = "D:\\Materiały\\SEMESTR 4\\Java - G. Górecki\\Zajęcia 3\\LAB03-scratch\\src\\example\\students.txt";
   //adress = "D:\\students.txt";
}
public void setAdress(String adress){
   this.adress = adress;
}

public String getAdress(){
    return this.adress;
}
/*
public void dodajObserwatora(Logger log){
    obserwatorzy.add(log);
}
 
public void usunObserwatora(Logger log){
    int index = obserwatorzy.indexOf(log);
    obserwatorzy.remove(index);
}
*/
 
public void notifyAdded(List<Student> previousStudents,List<Student> resultStudents){
    Student student;
    if(previousStudents == null){
        for(Logger l : loggers){
            for(Student s : resultStudents){
                l.log("----- ADDED: ", s);
            }
        }
    }else{
        List<Student> tmp  = new ArrayList<>(resultStudents);
        List<Student> tmp2 = new ArrayList<>(previousStudents); 
        tmp.removeAll(tmp2);
        ListIterator<Student> it = tmp.listIterator();
        while(it.hasNext()){
            student = it.next();
            for(Logger l : loggers){
              l.log("----- ADDED: ", student);
            }
        }
    }
    
    notifyIteration();
    System.out.println("Zakres wieku: <"+extractAge(resultStudents, MIN)+","+extractAge(resultStudents,MAX)+">\n");
    System.out.println("Zakres ocen: <"+extractMark(resultStudents, MIN)+","+extractMark(resultStudents,MAX)+">");
    extractStudents(resultStudents,MARK);
}
public void notifyRemoved(List<Student> previousStudents, List<Student> resultStudents){
   Student student;
   if(resultStudents == null){
        for(Logger l : loggers){
            for(Student s : previousStudents){
                l.log("----- REMOVED: ", s);
            }
        }
    }else{
        List<Student> tmp  = new ArrayList<>(previousStudents);
        List<Student> tmp2 = new ArrayList<>(resultStudents); 
            
        tmp.removeAll(tmp2);
        ListIterator<Student> it = tmp.listIterator();
    
        while(it.hasNext()){
            student = it.next();
            for(Logger l : loggers){
             l.log("----- REMOVED: ", student);
            }
        }
   }
    
    notifyIteration();
    System.out.println("Zakres wieku: <"+extractAge(resultStudents, MIN)+","+extractAge(resultStudents,MAX)+">\n");
    System.out.println("Zakres ocen: <"+extractMark(resultStudents, MIN)+","+extractMark(resultStudents,MAX)+">");
    extractStudents(resultStudents,MARK);
}

public void notifyUnchanged(){    
    for(Logger l : loggers){
        l.log(" ----- UNCHANGED ----- ");
    }
}

public void notifyIteration(){
    for(Logger l : loggers){
        l.log("------ ITERATION: ", i);
    }
}
        
public List<Student> getResults(){
    return resultStudents;
}

public void print(List<Student> students){
    for(Student s : students){
        System.out.println( s.getMark() + " " + s.getFirstName() + " " + s.getLastName() + " " + s.getAge() );
    } 
}

List<Student> extractStudents( List<Student> students, OrderMode mode ){// posortowani studenci 
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
double extractMark(List<Student> students, ExtremumMode mode ){// maksymalna lub minimalna ocena
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
int extractAge(List<Student> students, ExtremumMode mode ){// maksymalny lub minimalny wiek
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
        File f = new File( this.getAdress());
        if(f == null){
            throw new MyException();
        }
       resultStudents = StudentsParser.parse(f);
       i++; //iteracja pętli
              
       if(previousStudents == null){ //jeśli i = 1
           if(!resultStudents.isEmpty()){
                notifyAdded(previousStudents, resultStudents);
           }
       }
       else if(previousStudents.size() > resultStudents.size()){
           //usunięto
           notifyRemoved(previousStudents, resultStudents);
       }
       else if(previousStudents.size() < resultStudents.size()){
           //dodano
           notifyAdded(previousStudents, resultStudents);
        }
       else{
            // nie zmodyfikowano  
            notifyUnchanged();
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


 

    

