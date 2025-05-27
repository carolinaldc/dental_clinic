package dentalClinicPOJOS;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.Serializable;

public class Treatment implements Serializable {
	
	private static final long serialVersionUID = -7675148140680049781L;
	
	private Integer treatment_id;
	private String name; 
	private String description;
	private Integer price;
	private List<Appointment> appointments;
	private List<Material> materials ;
	
	public Treatment() {
		super();
	}
	
	public Treatment(String name, String description, Integer price, List<Appointment> appointments,
			List<Material> materials) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.appointments = appointments;
		this.materials = materials;
	}

	public Integer getTreatment_id() {
		return treatment_id;
	}

	public void setTreatment_id(Integer treatment_id) {
		this.treatment_id = treatment_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	@Override
	public int hashCode() {
		return Objects.hash(appointments, description, materials, name, price, treatment_id);
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
		return Objects.equals(appointments, other.appointments) && Objects.equals(description, other.description)
				&& Objects.equals(materials, other.materials) && Objects.equals(name, other.name)
				&& Objects.equals(price, other.price) && Objects.equals(treatment_id, other.treatment_id);
	}

	@Override
	public String toString() {
		return "Treatment [id: " + treatment_id + "name=" + name + ", description=" + description + ", price=" + price + ", appointments="
				+ appointments + ", materials=" + materials + "]";
	}
}
