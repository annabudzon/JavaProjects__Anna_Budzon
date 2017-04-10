package threads;

public class Main {

    public static void main(String[] args) {
        Monitor monitor = new Monitor();
        Thread close = new Thread(){
            @Override
            public void run(){
                try{ 
            Thread.sleep(30000); 
          }catch(InterruptedException e) {} 
        
        monitor.cancel();
            }
        };
        try {
            close.start();
            monitor.start();
        } catch (MonitorException ex) {}
        
        
    }
}
    

