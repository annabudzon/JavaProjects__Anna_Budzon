package fxml;

import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student{
    private final SimpleDoubleProperty mark = new SimpleDoubleProperty(0);
    private final SimpleStringProperty firstName = new SimpleStringProperty("");
    private final SimpleStringProperty lastName = new SimpleStringProperty("");
    private final SimpleIntegerProperty age = new SimpleIntegerProperty(0);
    
        
        public Student(){}
        
        
        @Override
        public String toString(){
            String result = firstName.get();
            result = new StringBuilder(result).append("   "+lastName.get()).toString();
            result = new StringBuilder(result).append("   "+mark.get()).toString();
            result = new StringBuilder(result).append("   "+age.get()).toString();
         return result;          
            
        }
        
        public Student(double mark, String firstName, String lastName, int age) {
            setMark(mark);
            setFirstName(firstName);
            setLastName(lastName);
            setAge(age);
    }

    public final DoubleProperty markProperty() {
        return this.mark;
    }
        
    public final double getMark() {
        return this.mark.get();
    }
 
    public final void setMark(final double m) {
        this.mark.set(m);
    }    
        
    public final StringProperty firstNameProperty() {
    return this.firstName;
    }
    
    public final String getFirstName() {
        return firstName.get();
    }
 
    public final void setFirstName(final String fName) {
        this.firstName.set(fName);
    }
       
    public final StringProperty lastNameProperty() {
        return this.lastName;
    }
    
    public final String getLastName() {
        return this.lastName.get();
    }
    
    public final void setLastName(final String fName) {
        this.lastName.set(fName);
    }
    
    public final IntegerProperty ageProperty() {
        return this.age;
    }
    
    public final int getAge() {
        return this.age.get();
    }
    
    public final void setAge(final int a) {
        this.age.set(a);
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        if (!Objects.equals(this.mark, other.mark)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.age, other.age)) {
            return false;
        }
        return true;
    }
	
}
