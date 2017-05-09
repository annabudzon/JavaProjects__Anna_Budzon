package message;

import java.io.Serializable;
import java.util.Objects;
import static message.MessageType.*;

public class MessageModel implements Serializable{

    private String userName;
    private int type;
    private String message;

    public MessageModel() {
    }

    public MessageModel(String userName, int type, String message) {
        this.userName = userName;
        this.type = type;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.userName);
        hash = 73 * hash + this.type;
        hash = 73 * hash + Objects.hashCode(this.userName);
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
        final MessageModel other = (MessageModel) obj;
        if (this.type != other.type) {
            return false;
        }
        if (this.message != other.message) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        switch (this.type) {
            case CONNECTING:
                result.append("CONNECTING:");
                result.append("   " + this.userName);
                result.append("   " + this.message);
                break;
            case CONNECTED:
                result.append("CONNECTED:");
                result.append("   " + this.userName);
                result.append("   " + this.message);
                break;
            case REGISTER:
                result.append("REGISTER:");
                result.append("   " + this.userName);
                result.append("   " + this.message);
                break;
            case LOGIN:
                result.append("LOGIN:");
                result.append("   " + this.userName);
                result.append("   " + this.message);
                break;
            case LOGOUT:
                result.append("LOGOUT:");
                result.append("   " + this.userName);
                result.append("   " + this.message);
                break;
            case MESSAGE:
                result.append("MESSAGE:");
                result.append("   " + this.userName);
                result.append(":   " + this.message);
                break;
            case ADDED:
                result.append("ADDED:");
                result.append("   " + this.userName);
                result.append("   " + this.message);
                break;
            case REMOVED:
                result.append("REMOVED:");
                result.append("   " + this.userName);
                result.append("   " + this.message);
                break;
            case ERROR:
                result.append("ERROR:");
                result.append("   " + this.userName);
                result.append("   " + this.message);
                break;
            default:
                break;
        }
        return result.toString();
    }

}
