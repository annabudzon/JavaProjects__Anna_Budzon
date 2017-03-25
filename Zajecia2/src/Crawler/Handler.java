package Crawler;

import java.util.List;

public interface Handler {
    public void notifyAdded(List<Student> previousStudents, List<Student> resultStudents);
    public void notifyRemoved(List<Student> previousStudents, List<Student> resultStudents);
    public void notifyIteration();
    public void notifyUnchanged();
}
