package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "participants")
public class Participants {

    public Participants() {
    }

    public Participants(ParticipantsId partId) {
        this.participantsId = partId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private ClientModel client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id", insertable = false, updatable = false)
    private TrainingModel training;

    @EmbeddedId
    private ParticipantsId participantsId;

    @Embeddable
    public static class ParticipantsId implements Serializable {

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "client_id")
        private ClientModel client;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "training_id")
        private TrainingModel training;

        public ParticipantsId() {
        }

        public ParticipantsId(ClientModel client, TrainingModel training) {
            this.client = client;
            this.training = training;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + Objects.hashCode(this.client);
            hash = 71 * hash + Objects.hashCode(this.training);
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
            final ParticipantsId other = (ParticipantsId) obj;
            if (!Objects.equals(this.client, other.client)) {
                return false;
            }
            if (!Objects.equals(this.training, other.training)) {
                return false;
            }
            return true;
        }

    }

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }

    public TrainingModel getTraining() {
        return training;
    }

    public void setTraining(TrainingModel training) {
        this.training = training;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.client);
        hash = 97 * hash + Objects.hashCode(this.training);
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
        final Participants other = (Participants) obj;
        if (!Objects.equals(this.client, other.client)) {
            return false;
        }
        if (!Objects.equals(this.training, other.training)) {
            return false;
        }
        return true;
    }

}
