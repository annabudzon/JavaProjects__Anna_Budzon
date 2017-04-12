package threads;

import java.util.ArrayList;
import java.util.List;
import static threads.Crawler.STATUS.*;

public class Monitor {
    private final List<String> links;
    private final List<MyThread> threads;
    private final int numberOfThreads;
    private boolean condition;
    public static Logger[] loggers = new Logger[]{ 
      new ConsoleLogger() 
    };
    
    public Monitor(){
    links = new ArrayList<>();
    threads = new ArrayList<>();
    links.add("D:\\students.txt");
    links.add("D:\\students2.txt");
    links.add("D:\\students3.txt");
    links.add("D:\\students4.txt");
    links.add("D:\\students5.txt");
    links.add("D:\\students6.txt");
    links.add("D:\\students7.txt");
    links.add("D:\\students8.txt");
    numberOfThreads = 8; 
         
    }
    
    public void iterationStartedEvent(Logger listener, int i){
	listener.log(I_STARTED,i);
    }
    
    public void iterationComplitedEvent(Logger listener, int i){
	listener.log(I_COMPLETED,i);
    }
    
    public void studentAddedEvent(Logger listener, Student s){
	listener.log(ADDED, s);
    }
	
    public void studentRemovedEvent(Logger listener, Student s){
	listener.log(REMOVED, s);
    }
    
    public void unchangedEvent(Logger listener){
	listener.log(UNCHANGED);
    }
        
    public void cancel(){
        try {
            condition = false;
            for(MyThread t : threads){
                t.postCancel();
                t.join();
            }
         
      }catch( InterruptedException e) {
         System.out.println("Interrupted thread!\n");
      } 
    }
    
    public void start() throws MonitorException{
        condition = true;
        int count = 0;
        
        while(condition){
            
            if(count == numberOfThreads-1){
               condition = false;
               break;
            }
            if(links.size() < numberOfThreads){
                throw new MonitorException("Zbyt duza ilosc watkow!");
            }
            
            String link = links.get(count);
            int idx = count+1;
            Crawler crawler = new Crawler(this);
            crawler.addNewStudentListener(new ParellelLogger(loggers)); 
            crawler.addRemoveStudentListener(new ParellelLogger(loggers)); 
            crawler.addUnchangedListener(new ParellelLogger(loggers));
            crawler.addIterationStartedListener(new ParellelLogger(loggers));
            crawler.addIterationComplitedListener(new ParellelLogger(loggers));
            String threadName = "THREAD "+idx;
            
            MyThread thread = new MyThread(threadName, crawler, link);
            threads.add(thread);
            thread.start();
            count++;         
        }
    }
}
