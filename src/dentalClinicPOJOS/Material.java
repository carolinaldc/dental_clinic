package dentalClinicPOJOS;
import java.util.List;
import java.util.Objects;
import java.io.Serializable;

public class Material implements Serializable{
	private static final long serialVersionUID = -6546395167641015440L;
	private Integer materials_id ;
	private String name ; 
	private Integer stock;
	private Supplier supplier ; 
	private List<Treatment> treatment; 
	
	   public Material(int id, String name, Integer stock) {
	        super();
	        this.materials_id = id; 
	        this.name = name;
	        this.stock = stock;
	    }



	public String getDescription() {
		return name;
	}

	public void setDescription(String description) {
		this.name = description;
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return materials_id;
	}

	public void setId(Integer id) {
		this.materials_id = id;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	@Override
	public int hashCode() {
		return Objects.hash(materials_id, name);
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
		return materials_id == other.materials_id && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Material [description=" + name + "]";
	} 

}
