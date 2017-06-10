package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "coach")
public class CoachModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coach_id")
    private int id;

    @Column(name = "coach_firstName")
    private String firstName;

    @Column(name = "coach_lastName")
    private String lastName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "coach", cascade = CascadeType.ALL)
    private List<TrainingModel> trainings = new ArrayList<>();

    public CoachModel() {
        this.trainings = new ArrayList<>();
    }
    
    public CoachModel(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.trainings = new ArrayList<>();
        
    }

    public CoachModel(int id, String firstName, String lastName, List<TrainingModel> trainings) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.trainings = trainings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<TrainingModel> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<TrainingModel> trainings) {
        this.trainings = trainings;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[" + this.id +"]");
        result.append("  " + this.firstName);
        result.append("  " + this.lastName + "\n");
        return result.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.firstName);
        hash = 47 * hash + Objects.hashCode(this.lastName);
        hash = 47 * hash + Objects.hashCode(this.trainings);
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
        final CoachModel other = (CoachModel) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.trainings, other.trainings)) {
            return false;
        }
        return true;
    }
}
