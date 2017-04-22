package model;

import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.codec.digest.DigestUtils;

public class UserModel {

    private final StringProperty userName;
    private final StringProperty password;
    private final StringProperty adress;
    private final StringProperty gender;
    private final IntegerProperty age;

    public UserModel() {
        this.userName = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.adress = new SimpleStringProperty();
        this.gender = new SimpleStringProperty();
        this.age = new SimpleIntegerProperty();
    }

    public UserModel(String userName, String password, String adress, String gender, Integer age) {
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(DigestUtils.sha1Hex(password));
        this.adress = new SimpleStringProperty(adress);
        this.gender = new SimpleStringProperty(gender);
        this.age = new SimpleIntegerProperty(age);
    }

    @Override
    public String toString() {
        return "UserModel{" + "userName=" + userName + ", password=" + password + ", adress=" + adress + ", gender=" + gender + ", age=" + age + '}';
    }

    public StringProperty getUserNameProperty() {
        return userName;
    }

    public StringProperty getPasswordProperty() {
        return password;
    }

    public StringProperty getAdressProperty() {
        return adress;
    }

    public StringProperty getGenderProperty() {
        return gender;
    }

    public IntegerProperty getAgeProperty() {
        return age;
    }

    public final String getUserName() {
        return userName.get();
    }

    public final String getPassword() {
        return password.get();
    }

    public final int getAge() {
        return age.get();
    }

    public final String getAdress() {
        return adress.get();
    }

    public final String getGender() {
        return userName.get();
    }

    public final void setUserName(String userName) {
        this.userName.set(userName);
    }

    public final void setPassword(String password) {
        this.password.set(DigestUtils.sha1Hex(password));
    }

    public final void setAge(int age) {
        this.age.set(age);
    }

    public final void setAdress(String adress) {
        this.adress.set(adress);
    }

    public final void setGender(String gender) {
        this.gender.set(gender);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.userName);
        hash = 17 * hash + Objects.hashCode(this.password);
        hash = 17 * hash + Objects.hashCode(this.adress);
        hash = 17 * hash + Objects.hashCode(this.gender);
        hash = 17 * hash + Objects.hashCode(this.age);
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
        final UserModel other = (UserModel) obj;
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.adress, other.adress)) {
            return false;
        }
        if (!Objects.equals(this.gender, other.gender)) {
            return false;
        }
        if (!Objects.equals(this.age, other.age)) {
            return false;
        }
        return true;
    }

}
