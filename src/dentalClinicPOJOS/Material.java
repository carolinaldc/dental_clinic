package dentalClinicPOJOS;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Material")
@XmlType(propOrder = {"supplier", "name"})
public class Material implements Serializable{
	
	private static final long serialVersionUID = -6546395167641015440L;
	
	@XmlTransient
	private Integer materials_id ;
	@XmlElement
	private String name ; 
	@XmlElement
	private Supplier supplier ; 
	@XmlTransient
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
	
	public Material(String name, Supplier supplier) {
		super();
		this.name = name;
		this.supplier = supplier;
	}
	
	public Material(Integer materials_id, String name, Supplier supplier, List<Treatment> treatments) {
		super();
		this.materials_id = materials_id;
		this.name = name;
		this.supplier = supplier;
		this.treatments= treatments;
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
