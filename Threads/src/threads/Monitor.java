package threads;

import java.util.ArrayList;
import java.util.List;

public class Monitor {
    private List<String> links;
    private int numberOfThreads;
    private boolean condition;
    private List<Thread> threads;
     public static Logger[] loggers = new Logger[]{ 
      new ConsoleLogger() 
      //new MailLogger()
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
    
   private final List<Logger> addNewStudentListeners = new ArrayList<>();
	public void addNewStudentListener(Logger listener){
		addNewStudentListeners.add(listener);
	}
	public void removeNewStudentListener(Logger listener){
		addNewStudentListeners.remove(listener);
	}
        
	private final List<Logger> addRemoveStudentListeners = new ArrayList<>();
	public void addRemoveStudentListener(Logger listener){
		addRemoveStudentListeners.add(listener);
	}
	public final void removeRemoveStudentListener(Logger listener){
		addRemoveStudentListeners.remove(listener);
	}
        
    public void cancel(){
        try {
         condition = false;
         Crawler.postCancel();
         for(Thread t : threads){
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
               // this.cancel();
               break;
            }
            if(links.size() < numberOfThreads){
                throw new MonitorException("Zbyt duza ilosc watkow!");
            }
            
            String l = links.get(count);
            Crawler crawler = new Crawler();
            int idx = count+1;
            
            Thread t = new Thread(){ 
                @Override
                public void run(){  
                    this.setName("THREAD "+idx);
                    crawler.addNewStudentListener(new ParellelLogger(loggers)); 
                    crawler.addRemoveStudentListener(new ParellelLogger(loggers)); 
                    crawler.addUnchangedListener(new ParellelLogger(loggers));
                    crawler.addIterationStartedListener(new ParellelLogger(loggers));
                    crawler.addIterationComplitedListener(new ParellelLogger(loggers));
                    crawler.run(l);
                }  
            };  
            threads.add(t);
            t.start();
            count++;
        }
    }
}
