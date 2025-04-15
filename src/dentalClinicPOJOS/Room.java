package dentalClinicPOJOS;

import java.io.Serializable;
import java.util.Objects;


public class Room implements Serializable{
	
	
	private static final long serialVersionUID = -7099651662171602398L;
	private String id_room ; 
	private String description;
	private Status status;
	
	public enum Status {
        AVAILABLE, OCCUPIED //acordarnos que luego tenemos que pasarlo a string para pasarlo a SQL
    }
	
	public Room() {
		super();
	}

	public String getID() {
		return id_room;
	}

	public void setID(String iD) {
		id_room = iD;
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
		return Objects.hash(description, id_room, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		return Objects.equals(description, other.description) && Objects.equals(id_room, other.id_room)
				&& status == other.status;
	}

	@Override
	public String toString() {
		return "Room [description=" + description + ", status=" + status + "]";
	}
	
	
	 
}
