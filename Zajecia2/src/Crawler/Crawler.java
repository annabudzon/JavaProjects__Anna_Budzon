package Crawler;

import static Crawler.Crawler.ExtremumMode.*;
import static Crawler.Crawler.OrderMode.*;
import static Crawler.Crawler.STATUS.*;
import static Crawler.Sorter.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Crawler implements Runnable {

private static List<Student> resultStudents;
private static List<Student> previousStudents;
public static String adress = "D:\\students.txt";
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
public enum STATUS
{
STARTING,
I_STARTED,
I_COMPLETED,
ADDED,
REMOVED,
UNCHANGED
}

    
public Crawler() {
    previousStudents = new ArrayList<>();
    //adress = "D:\\Materiały\\SEMESTR 4\\Java - G. Górecki\\Zajęcia 3\\LAB03-scratch\\src\\example\\students.txt";
}
         
public static List<Student> getResults(){
    return resultStudents;
}

public static void print(List<Student> students){ //wyświetlanie
    students.forEach((s) -> {
        System.out.println( s.getMark() + " " + s.getFirstName() + " " + s.getLastName() + " " + s.getAge() );
    }); 
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
       
        private List<Logger> iterationStartedListeners = new ArrayList<>();
	public void addIterationStartedListener(Logger listener){
		iterationStartedListeners.add(listener);
	}
	public void removeIterationStartedListener(Logger listener){
		iterationStartedListeners.remove(listener);
	}
	private List<Logger> iterationComplitedListeners = new ArrayList<>();
	public void addIterationComplitedListener(Logger listener){
		iterationComplitedListeners.add(listener);
	}
	public void removeIterationComplitedListener(Logger listener){
		iterationComplitedListeners.remove(listener);
	}
        
        private List<Logger> addNewStudentListeners = new ArrayList<>();
	public void addNewStudentListener(Logger listener){
		addNewStudentListeners.add(listener);
	}
	public void removeNewStudentListener(Logger listener){
		addNewStudentListeners.remove(listener);
	}
        
	private List<Logger> addRemoveStudentListeners = new ArrayList<>();
	public void addRemoveStudentListener(Logger listener){
		addRemoveStudentListeners.add(listener);
	}
	public void removeRemoveStudentListener(Logger listener){
		addRemoveStudentListeners.remove(listener);
	}
        
        private List<Logger> addUnchangedListeners = new ArrayList<>();
	public void addUnchangedListener(Logger listener){
		addUnchangedListeners.add(listener);
	}
	public void removeUnchangedListener(Logger listener){
		addUnchangedListeners.remove(listener);
	}
        
@Override
public void run(){
    int i = 1;
    StudentsListener handler;
    Student student;
    while(true){
        for(Logger l:iterationStartedListeners){
            l.log(I_STARTED,i);
        }
        File f = new File(adress); 
       resultStudents = new ArrayList<>();
        try {
            resultStudents = StudentsParser.parse(f);
        } catch (IOException ex) {} 
             
       if(previousStudents == null){ //jeśli i = 1
           if(!resultStudents.isEmpty()){
                for (Logger l : addNewStudentListeners) {
                    for (Student s : resultStudents) {
                        l.log(ADDED, s);
                    }
            }
           }
       }
       else if(resultStudents == null){ //jeśli i = 1
           if(!previousStudents.isEmpty()){
                for (Logger l : addRemoveStudentListeners) {
                    for (Student s : previousStudents) {
                        l.log(REMOVED, s);
                    }
            }
           }
       }
       else if(previousStudents.size() > resultStudents.size()){
           //usunięto
           handler = new StudentsListener();
           List<Student> st = handler.removed(previousStudents, resultStudents);
           for(Student s: st){
                for (Logger l : addRemoveStudentListeners) {
                    l.log(REMOVED, s);
                }
            }
       }
       else if(previousStudents.size() < resultStudents.size()){
           //dodano
           handler = new StudentsListener();
           List<Student> st = handler.added(previousStudents, resultStudents);
           for(Student s : st){
                for (Logger l : addNewStudentListeners) {
                    l.log(ADDED, s);
                }
        }
        }
       else{
            // nie zmodyfikowano  
            for (Logger l : addUnchangedListeners) {
                l.log(UNCHANGED);
            }
       }
       
       previousStudents = resultStudents;
       
        try {
            Thread.sleep(10000);
         } catch (Exception e) {
            System.out.println(e);
         }
        i++; //iteracja pętli
        for(Logger l: iterationComplitedListeners){
            l.log(I_COMPLETED,i);
        }
        
    }
}

}//class


 

    

