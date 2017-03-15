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
    loggers = new Logger[] { new ConsoleLogger(), new MailLogger() };
            
    resultStudents = new ArrayList<>();
    adress = "D:\\Materiały\\SEMESTR 4\\Java - G. Górecki\\Zajęcia 3\\LAB03-scratch\\src\\example\\students.txt";
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
 
public void powiadom_dodano(Student student){
    for(Logger l : loggers){
        l.log("ADDED: ", student);
    }
}
public void powiadom_usunieto(Student student){
    for(Logger l : loggers){
        l.log("REMOVED: ", student);
    }
}

public void powiadom_iteracja(){
    for(Logger l : loggers){
        l.log("Iteracja: ", i);
    }
}
        
public List<Student> getResults(){
    return resultStudents;
}

public void print(List<Student> students){
    for(Student s : students){
        System.out.println( s.getMark() + " " + s.getFirstName() + " " + s.getLastName() + " " + s.getAge() );
    } 
    i++;
}
public List<Student> addStudent(List<Student> students, Student s){
    if (students.contains(s)) {
        System.out.println("\nStudent już istnieje.");
    } else {
        students.add(s);
        powiadom_dodano(s);
    }
    return students;
}
public List<Student> removeStudent(List<Student> students, int idx){
    if(idx < students.size()){
        students.remove(idx);
        powiadom_usunieto(students.get(idx));
    }
    else{
        throw new IndexOutOfBoundsException("Index poza zakresem!");
    }
    return students;
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
        resultStudents = StudentsParser.parse( f, this );
        powiadom_iteracja();
        System.out.println("Zakres wieku: <"+extractAge(resultStudents, MIN)+","+extractAge(resultStudents,MAX)+">\n");
        System.out.println("Zakres ocen: <"+extractMark(resultStudents, MIN)+","+extractMark(resultStudents,MAX)+">");
        extractStudents(resultStudents,MARK);
        Student s = new Student();
        addStudent(resultStudents, s);
        powiadom_iteracja();
        System.out.println("Zakres wieku: <"+extractAge(resultStudents, MIN)+","+extractAge(resultStudents,MAX)+">\n");
        System.out.println("Zakres ocen: <"+extractMark(resultStudents, MIN)+","+extractMark(resultStudents,MAX)+">");
       extractStudents(resultStudents,MARK);
        removeStudent(resultStudents, 3);
        powiadom_iteracja();
        System.out.println("Zakres wieku: <"+extractAge(resultStudents, MIN)+","+extractAge(resultStudents,MAX)+">\n");
        System.out.println("Zakres ocen: <"+extractMark(resultStudents, MIN)+","+extractMark(resultStudents,MAX)+">");
        extractStudents(resultStudents,MARK);
        
        try {
            Thread.sleep(10000);
         } catch (Exception e) {
            System.out.println(e);
         }
    }
}

}//class


 

    

