package model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "training")
public class TrainingModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_id")
    private int id;

    @Column(name = "date")
    private Date date;

    @Column(name = "time")
    private Time time;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id")
    private CoachModel coach;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "kind_id")
    private KindOfTraining kind;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "trainings")
    private List<ClientModel> clients = new ArrayList<>();
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "participantsId.training")
    //private List<Participants> clients = new ArrayList<>();
      
    public TrainingModel() {
    }

    public TrainingModel(int id, Date date, Time time, CoachModel coach, Place place, KindOfTraining kind) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.coach = coach;
        this.place = place;
        this.kind = kind;
    }

    public TrainingModel(Date date, Time time, CoachModel coach, Place place, KindOfTraining kind) {
        this.date = date;
        this.time = time;
        this.coach = coach;
        this.place = place;
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public CoachModel getCoach() {
        return coach;
    }

    public void setCoach(CoachModel coach) {
        this.coach = coach;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public KindOfTraining getKind() {
        return kind;
    }

    public void setKind(KindOfTraining kind) {
        this.kind = kind;
    }

    public List<ClientModel> getClients() {
        return clients;
    }

    public void setClients(List<ClientModel> clients) {
        this.clients = clients;
    }
    
    /*public void addClient(ClientModel client) {
        
        Participants participants = new Participants(new Participants.ParticipantsId(client, this));

        participants.setClient(client);
        participants.setTraining(this);

        clients.add(participants);
    }
    
    public void deleteClient(ClientModel client) {
        
        Participants participants = new Participants(new Participants.ParticipantsId(client, this));

        participants.setClient(client);
        participants.setTraining(this);

        clients.remove(participants);
    }*/

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[" + this.id + "]");
        result.append("  Data:" + this.date);
        result.append("  Godzina: " + this.time + "\n");
        result.append("  Miejsce: " + this.place);
        result.append("  Rodzaj: " + this.kind);
        return result.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.id;
        hash = 11 * hash + Objects.hashCode(this.date);
        hash = 11 * hash + Objects.hashCode(this.time);
        hash = 11 * hash + Objects.hashCode(this.coach);
        hash = 11 * hash + Objects.hashCode(this.place);
        hash = 11 * hash + Objects.hashCode(this.kind);
        hash = 11 * hash + Objects.hashCode(this.clients);
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
        final TrainingModel other = (TrainingModel) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        if (!Objects.equals(this.coach, other.coach)) {
            return false;
        }
        if (!Objects.equals(this.place, other.place)) {
            return false;
        }
        if (!Objects.equals(this.kind, other.kind)) {
            return false;
        }
        if (!Objects.equals(this.clients, other.clients)) {
            return false;
        }
        return true;
    }

}
