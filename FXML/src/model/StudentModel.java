package model;

import java.io.Serializable;
import java.util.Objects;

public class StudentModel implements Serializable {

    private double mark;
    private String firstName;
    private String lastName;
    private int age;

    public StudentModel() {
    }

    public StudentModel(double mark, String firstName, String lastName, int age) {
        this.mark = mark;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public StudentModel(StudentModel s) {
        this.mark = s.getMark();
        this.firstName = s.getFirstName();
        this.lastName = s.getLastName();
        this.age = s.getAge();

    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("   " + this.firstName);
        result.append("   " + this.lastName);
        result.append("   " + this.mark);
        result.append("   " + this.age);
        return result.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.mark) ^ (Double.doubleToLongBits(this.mark) >>> 32));
        hash = 71 * hash + Objects.hashCode(this.firstName);
        hash = 71 * hash + Objects.hashCode(this.lastName);
        hash = 71 * hash + this.age;
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
        final StudentModel other = (StudentModel) obj;
        if (Double.doubleToLongBits(this.mark) != Double.doubleToLongBits(other.mark)) {
            return false;
        }
        if (this.age != other.age) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        return true;
    }

}
