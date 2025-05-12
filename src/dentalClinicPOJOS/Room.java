package dentalClinicPOJOS;

import java.io.Serializable;
import java.util.Objects;

public class Room implements Serializable {

    private static final long serialVersionUID = -7099651662171602398L;

    private int room_id; // cambiado a int para coincidir con la tabla SQL
    private String description;
    private Status status;

    public enum Status {
        AVAILABLE, OCCUPIED
    }

    // Constructor sin ID (por ejemplo, para insertar)
    public Room(String description, Status status) {
        this.description = description;
        this.status = status;
    }

    // Constructor con ID (por ejemplo, para leer desde la BD)
    public Room(int room_id, String description, Status status) {
        this.room_id = room_id;
        this.description = description;
        this.status = status;
    }
    

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, room_id, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room other = (Room) obj;
        return room_id == other.room_id &&
               Objects.equals(description, other.description) &&
               status == other.status;
    }

    @Override
    public String toString() {
        return "Room [id=" + room_id + ", description=" + description + ", status=" + status + "]";
    }

}
