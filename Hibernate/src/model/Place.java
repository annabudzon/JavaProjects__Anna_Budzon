package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "places")
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private int id;

    @Column(name = "place")
    private String place;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    //@JoinColumn(name = "training_id", nullable = false)
    private List<TrainingModel> trainings = new ArrayList<>();

    public Place() {
    }

    public Place(int id, String place, List<TrainingModel> trainings) {
        this.id = id;
        this.place = place;
        this.trainings = trainings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<TrainingModel> getTraining() {
        return trainings;
    }

    public void setTrainings(List<TrainingModel> training) {
        this.trainings = training;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.place + "\n");
        return result.toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.place);
        hash = 41 * hash + Objects.hashCode(this.trainings);
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
        final Place other = (Place) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.place, other.place)) {
            return false;
        }
        if (!Objects.equals(this.trainings, other.trainings)) {
            return false;
        }
        return true;
    }

    
}
