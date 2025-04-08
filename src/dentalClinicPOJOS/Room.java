package dentalClinicPOJOS;

import java.io.Serializable;
import java.util.Objects;

public class Room implements Serializable{
	private static final long serialVersionUID = -7099651662171602398L;
	private String ID ; 
	private String description ;
	
	
	public Room() {
		super();
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID, description);
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
		return Objects.equals(ID, other.ID) && Objects.equals(description, other.description);
	}

	@Override
	public String toString() {
		return "Room [description=" + description + "]";
	} 
}
