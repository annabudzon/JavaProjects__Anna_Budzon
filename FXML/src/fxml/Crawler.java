package fxml;

import static fxml.Crawler.STATUS.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Crawler implements Runnable {

private static List<Student> resultStudents;
private static List<Student> previousStudents;
public static String adress = "D:\\students.txt";

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


 

    

