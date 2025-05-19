package dentalClinicPOJOS;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Supplier")
@XmlType(propOrder = {"supplierName", "phone", "email"})
public class Supplier implements Serializable {
	
    private static final long serialVersionUID = 869952558751098968L;
    
    @XmlTransient
    private Integer supplier_id;
    @XmlElement
    private String supplierName;
    @XmlElement
    private Integer phone;
    @XmlElement
    private String email;
    @XmlTransient
    private List<Material> material;
    
	public Supplier() {
		super();
	}

	public Supplier(String supplierName, Integer phone, String email, List<Material> material) {
		super();
		this.supplierName = supplierName;
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

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
		return Objects.hash(email, material, supplierName, phone, supplier_id);
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
				&& Objects.equals(supplierName, other.supplierName) && Objects.equals(phone, other.phone)
				&& Objects.equals(supplier_id, other.supplier_id);
	}

	@Override
	public String toString() {
		return "Supplier [supplierName=" + supplierName + ", phone=" + phone + ", email=" + email + ", material=" + material + "]";
	}
}
