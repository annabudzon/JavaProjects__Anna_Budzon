package threads;

import static threads.Crawler.STATUS.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Crawler {
private final List<Logger> addNewStudentListeners = new ArrayList<>();
private final List<Logger> iterationStartedListeners = new ArrayList<>();
private final List<Logger> iterationComplitedListeners = new ArrayList<>();
private final List<Logger> addRemoveStudentListeners = new ArrayList<>();
private final List<Logger> addUnchangedListeners = new ArrayList<>();
private List<Student> resultStudents;
private List<Student> previousStudents;
private final Monitor monitor;
public boolean condition;
public String adress;

public enum STATUS
{
STARTING,
I_STARTED,
I_COMPLETED,
ADDED,
REMOVED,
UNCHANGED
}
  
public Crawler(Monitor monitor) {
    this.monitor = monitor;
    previousStudents = new ArrayList<>();
    //adress = "D:\\Materiały\\SEMESTR 4\\Java - G. Górecki\\Zajęcia 3\\LAB03-scratch\\src\\example\\students.txt";
}
       
public void addIterationStartedListener(Logger listener){
    iterationStartedListeners.add(listener);
}

public void removeIterationStartedListener(Logger listener){
    iterationStartedListeners.remove(listener);
}

public void addIterationComplitedListener(Logger listener){
    iterationComplitedListeners.add(listener);
}

public final void removeIterationComplitedListener(Logger listener){
    iterationComplitedListeners.remove(listener);
}
        
public void addNewStudentListener(Logger listener){
    addNewStudentListeners.add(listener);
}

public void removeNewStudentListener(Logger listener){
    addNewStudentListeners.remove(listener);
}
        
public void addRemoveStudentListener(Logger listener){
    addRemoveStudentListeners.add(listener);
}

public final void removeRemoveStudentListener(Logger listener){
    addRemoveStudentListeners.remove(listener);
}
        
public void addUnchangedListener(Logger listener){
    addUnchangedListeners.add(listener);
}

public void removeUnchangedListener(Logger listener){
    addUnchangedListeners.remove(listener);
}

public void postCancel(){
    condition = false;
}     
           
public synchronized void run(String adr){
    condition = true;
    this.adress = adr;
    int i = 1;
    StudentsListener handler;
    while(condition){
        for(Logger l:iterationStartedListeners){
            monitor.iterationStartedEvent(l,i);
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
                        monitor.studentAddedEvent(l,s);
                    }
                }
            }
       }
       else if(resultStudents == null){ //jeśli i = 1
           if(!previousStudents.isEmpty()){
                for (Logger l : addRemoveStudentListeners) {
                    for (Student s : previousStudents) {
                        monitor.studentRemovedEvent(l,s);
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
                    monitor.studentRemovedEvent(l,s);
                   
                }
            }
       }
       else if(previousStudents.size() < resultStudents.size()){
           //dodano
           handler = new StudentsListener();
           List<Student> st = handler.added(previousStudents, resultStudents);
           for(Student s : st){
                for (Logger l : addNewStudentListeners) {
                    monitor.studentAddedEvent(l,s);
                }
            }
        }
       else{
            // nie zmodyfikowano  
            for (Logger l : addUnchangedListeners) {
                monitor.unchangedEvent(l);
            }
       }
       
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        
        for(Logger l: iterationComplitedListeners){
            monitor.iterationComplitedEvent(l,i);
        }
        
        previousStudents = resultStudents;
        i++; //iteracja pętli
    }
}
}//class


 

    

