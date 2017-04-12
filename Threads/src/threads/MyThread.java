
package threads;

public class MyThread extends Thread {
    private final Crawler crawler;
    private final String adress;
    private final String threadName;
    private Thread thread;
    
    public MyThread(String name, Crawler crawler, String adress){
        this.crawler = crawler;
        this.adress  = adress;
        this.threadName = name;
    }
    
    @Override
    public void run() {
        crawler.run(this.adress);
    }

    @Override
    public void start() {
      if (thread == null) {
         thread = new Thread(this, threadName);
         thread.start();
      }
    }
    
    public void postCancel(){
        crawler.postCancel();
    }    
}
