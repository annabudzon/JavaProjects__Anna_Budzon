
package fxml;

import fxml.Crawler.STATUS;

public interface Logger {
   public void log(STATUS status, Student student);
   public void log(STATUS status, int iteracja);
   public void log(STATUS status);
}
