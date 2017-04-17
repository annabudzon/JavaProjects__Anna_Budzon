
package logger;

import fxml.Crawler.STATUS;
import controller.MyController;
import model.Student;

public interface Logger {
   public void log(STATUS status, Student student, MyController control);
   public void log(STATUS status, int iteracja);
   public void log(STATUS status, MyController control);
}
