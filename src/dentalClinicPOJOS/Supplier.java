package dentalClinicPOJOS;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Supplier implements Serializable {
	
    private static final long serialVersionUID = 869952558751098968L;
    
    private Integer supplier_id;
    private String name;
    private Integer phone;
    private String email;
    private List<Material> material;
    
	public Supplier() {
		super();
	}

	public Supplier(String name, Integer phone, String email, List<Material> material) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.material = material;
	}

	public Integer getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Material> getMaterial() {
		return material;
	}

	public void setMaterial(List<Material> material) {
		this.material = material;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, material, name, phone, supplier_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Supplier other = (Supplier) obj;
		return Objects.equals(email, other.email) && Objects.equals(material, other.material)
				&& Objects.equals(name, other.name) && Objects.equals(phone, other.phone)
				&& Objects.equals(supplier_id, other.supplier_id);
	}

	@Override
	public String toString() {
		return "Supplier [name=" + name + ", phone=" + phone + ", email=" + email + ", material=" + material + "]";
	}
}
