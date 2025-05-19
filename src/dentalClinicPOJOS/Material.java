package dentalClinicPOJOS;
import java.util.List;
import java.util.Objects;
import java.io.Serializable;

public class Material implements Serializable{
	
	private static final long serialVersionUID = -6546395167641015440L;
	
	private Integer materials_id ;
	private String name ; 
	private Supplier supplier ; 
	private List<Treatment> treatments;
	
	public Material() {
		super();
	}

	public Material(String name, Supplier supplier, List<Treatment> treatments) {
		super();
		this.name = name;
		this.supplier = supplier;
		this.treatments = treatments;
	}

	public Integer getMaterials_id() {
		return materials_id;
	}

	public void setMaterials_id(Integer materials_id) {
		this.materials_id = materials_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public List<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}

	@Override
	public int hashCode() {
		return Objects.hash(materials_id, name, supplier, treatments);
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
		return Objects.equals(materials_id, other.materials_id) && Objects.equals(name, other.name)
				&& Objects.equals(supplier, other.supplier) && Objects.equals(treatments, other.treatments);
	}

	@Override
	public String toString() {
		return "Material [name=" + name + ", supplier=" + supplier + ", treatments=" + treatments + "]";
	} 
}
