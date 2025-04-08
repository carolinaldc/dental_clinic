package dentalClinicPOJOS;
import java.util.Objects;
import java.io.Serializable;

public class Material implements Serializable{
	private static final long serialVersionUID = -6546395167641015440L;
	private String description ; 
	private int ID ;
	
	public Material() {
		super();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
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
		Material other = (Material) obj;
		return ID == other.ID && Objects.equals(description, other.description);
	}

	@Override
	public String toString() {
		return "Material [description=" + description + "]";
	} 

}
