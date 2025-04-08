package dentalClinicPOJOS;
import java.util.Objects;
import java.io.Serializable;

public class Treatment implements Serializable {
	private static final long serialVersionUID = -7675148140680049781L;
	private int id;
	private String description;
	private String tools;
	

	public Treatment() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTools() {
		return tools;
	}

	public void setTools(String tools) {
		this.tools = tools;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, tools);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Treatment other = (Treatment) obj;
		return Objects.equals(description, other.description) && id == other.id && Objects.equals(tools, other.tools);
	}



	@Override
	public String toString() {
		return "Treatment [description=" + description + ", tools=" + tools + "]";
	} 
	

	
}
