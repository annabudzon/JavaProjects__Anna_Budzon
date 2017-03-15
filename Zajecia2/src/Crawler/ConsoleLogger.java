package Crawler;

public class ConsoleLogger implements Logger{
   public ConsoleLogger(){}
   @Override
   public void log( String status, Student student){
       System.out.println(status+student);
   }
   @Override
   public void log(String status, int iteracja){
       System.out.println(status+iteracja);
   }

}
