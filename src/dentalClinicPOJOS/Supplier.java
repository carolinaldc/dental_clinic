package dentalClinicPOJOS;
import java.io.Serializable;
import java.util.Objects;

public class Supplier implements Serializable {
	private static final long serialVersionUID = 869952558751098968L;
	private int supplier_id ;
	private String name  ; 
	private String surname; 
	private String phone ;
	private String email ;
	private String address ; 
	private Material material; 
	
	//hacer getters y setters the surname, phone email
	
	public Supplier () {
		super(); 
	}

	public int getCIF() {
		return supplier_id;
	}

	public void setCIF(int CIF) {
		this.supplier_id = CIF;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(supplier_id, address, name);
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
		return supplier_id == other.supplier_id && Objects.equals(address, other.address)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Supplier [name=" + name  + ", address=" + address + "]";
	}

	
	
	
}
