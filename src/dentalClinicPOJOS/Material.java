package dentalClinicPOJOS;
import java.util.Objects;
import java.io.Serializable;

public class Material implements Serializable{
	private static final long serialVersionUID = -6546395167641015440L;
	private String name ; 
	private int id ;
	private int stock;
	
	public Material() {
		super();
	}

	public String getDescription() {
		return name;
	}

	public void setDescription(String description) {
		this.name = description;
	}

	public int getID() {
		return id;
	}
	
	public void setID(int iD) {
		id = iD;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
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
		return id == other.id && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Material [description=" + name + "]";
	} 

}
