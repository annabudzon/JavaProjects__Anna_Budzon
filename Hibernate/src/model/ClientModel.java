package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "client")
public class ClientModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private int id;

    @Column(name = "client_firstName")
    private String firstName;

    @Column(name = "client_lastName")
    private String lastName;

    @Column(name = "residence")
    private String residence;

    @Column(name = "age")
    private int age;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "participants",
            uniqueConstraints = {
                @UniqueConstraint(columnNames = {"client_id", "training_id"})},
            joinColumns = {
                @JoinColumn(name = "client_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "training_id")})
    //@OneToMany(cascade=CascadeType.ALL, mappedBy="ParticipantsId.training")
    private List<TrainingModel> trainings = new ArrayList();

    public ClientModel() {
        this.trainings = new ArrayList<>();
    }

    public ClientModel(String firstName, String lastName, String residence, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.residence = residence;
        this.age = age;
        this.trainings = new ArrayList<>();
    }

    public ClientModel(int id, String firstName, String lastName, String residence, int age, List<TrainingModel> trainings) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.residence = residence;
        this.age = age;
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

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
        result.append("[" + this.id + "]");
        result.append("  " + this.firstName);
        result.append("  " + this.lastName + "\n");
        return result.toString();
    }

    /*public void addTraining(TrainingModel training) {
        // Notice a JoinedUserRole object
        Participants participants = new Participants(new Participants.ParticipantsId(this, training));

        participants.setTraining(training);
        participants.setClient(this);

        trainings.add(participants);
    }
     
     public void deleteTraining(TrainingModel training) {
        // Notice a JoinedUserRole object
        Participants participants = new Participants(new Participants.ParticipantsId(this, training));

        participants.setTraining(training);
        participants.setClient(this);

        trainings.remove(participants);
    }*/
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this.id;
        hash = 19 * hash + Objects.hashCode(this.firstName);
        hash = 19 * hash + Objects.hashCode(this.lastName);
        hash = 19 * hash + Objects.hashCode(this.residence);
        hash = 19 * hash + this.age;
        hash = 19 * hash + Objects.hashCode(this.trainings);
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
        final ClientModel other = (ClientModel) obj;
        if (this.id != other.id) {
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
        if (!Objects.equals(this.residence, other.residence)) {
            return false;
        }
        if (!Objects.equals(this.trainings, other.trainings)) {
            return false;
        }
        return true;
    }

}
