package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "kinds")
public class KindOfTraining implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kind_id")
    private int id;

    @Column(name = "kind")
    private String kind;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "kind")
    private List<TrainingModel> trainings = new ArrayList<>();

    public KindOfTraining() {
    }

    public KindOfTraining(int id, String kind, List<TrainingModel> trainings) {
        this.id = id;
        this.kind = kind;
        this.trainings = trainings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
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
        result.append(this.kind + "\n");
        return result.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.kind);
        hash = 97 * hash + Objects.hashCode(this.trainings);
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
        final KindOfTraining other = (KindOfTraining) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.kind, other.kind)) {
            return false;
        }
        if (!Objects.equals(this.trainings, other.trainings)) {
            return false;
        }
        return true;
    }
    
    

}
