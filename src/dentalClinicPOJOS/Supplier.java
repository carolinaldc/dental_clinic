package dentalClinicPOJOS;
import java.io.Serializable;
import java.util.Objects;

public class Supplier implements Serializable {
	private static final long serialVersionUID = 869952558751098968L;
	private int CIF ;
	private String name  ; 
	private String contact ;
	private String address ; 
	
	public Supplier () {
		super(); 
	}

	public int getCIF() {
		return CIF;
	}

	public void setCIF(int CIF) {
		this.CIF = CIF;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(CIF, address, contact, name);
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
		return CIF == other.CIF && Objects.equals(address, other.address) && Objects.equals(contact, other.contact)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Supplier [name=" + name + ", contact=" + contact + ", address=" + address + "]";
	}

	
	
	
}
