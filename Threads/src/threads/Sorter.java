package threads;

import java.util.*;

public class Sorter {
    
    public static List<Student> sortMark(List<Student> students){
    List<Student> resultStudents = new ArrayList<>();
    resultStudents = students;
    Collections.sort(resultStudents, 
                        (o1, o2) -> Double.compare(o1.getMark(), o2.getMark()));
    
        return resultStudents;
    }
    
    public static List<Student> sortName(List<Student> students){
        List<Student> resultStudents = new ArrayList<>();
        resultStudents = students;
        Collections.sort(resultStudents, 
                        (o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName()));
        return resultStudents;
    }
    
    public static List<Student> sortLastName(List<Student> students){
        List<Student> resultStudents = new ArrayList<>();
        resultStudents = students;
        Collections.sort(resultStudents, 
                        (o1, o2) -> o1.getLastName().compareTo(o2.getLastName()));
        return resultStudents;
    }
    
    public static List<Student> sortAge(List<Student> students){
        List<Student> resultStudents = new ArrayList<>();
        resultStudents = students;
        Collections.sort(resultStudents, 
                        (o1, o2) -> Double.compare(o1.getMark(), o2.getMark()));
        return resultStudents;
    }
    
    public static double maxMark(List<Student> students){
       Student s =  Collections.max(students, (o1, o2) -> Double.compare(o1.getMark(), o2.getMark()));
        return s.getMark();
    }
    
    public static double minMark(List<Student> students){
        Student s = Collections.min(students, (o1, o2) -> Double.compare(o1.getMark(), o2.getMark()));
        return s.getMark();
    }
    
    public static int maxAge(List<Student> students){
        Student s = Collections.max(students, (o1, o2) -> Integer.compare(o1.getAge(), o2.getAge()));
        return s.getAge();
    }
    
    public static int minAge(List<Student> students){
        Student s =  Collections.min(students, (o1, o2) -> Integer.compare(o1.getAge(), o2.getAge()));
        return s.getAge();
    }
}
