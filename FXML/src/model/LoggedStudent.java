package model;

import crawler.Crawler.STATUS;
import java.util.List;

public class LoggedStudent extends StudentModel {

    private long time;
    private STATUS status;

     public LoggedStudent(){}
    
    public LoggedStudent(StudentModel student, STATUS status) {
        super(student);
        this.time = System.currentTimeMillis() / 1000L;
        this.status = status;
    }

    public LoggedStudent(long time, STATUS status, List<LoggedStudent> loggedStudents, double mark, String firstName, String lastName, int age) {
        super(mark, firstName, lastName, age);
        this.time = time;
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.time);
        result.append("   " + this.status + ": ");
        result.append("   " + getFirstName());
        result.append("   " + getLastName());
        result.append("   " + getMark());
        result.append("   " + getAge());

        return result.toString();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

}
