package dentalClinicPOJOS;
import java.util.Objects;
import java.io.Serializable;

public class Material implements Serializable{
	private static final long serialVersionUID = -6546395167641015440L;
	private String name ; 
	private Integer id ;
	private Integer stock;
	
	public Material(Integer stock, String name) {
		super();
		this.stock = stock;
		this.name = name;
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
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
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
