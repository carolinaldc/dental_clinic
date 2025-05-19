package dentalClinicPOJOS;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.Serializable;

public class Treatment implements Serializable {
	
	private static final long serialVersionUID = -7675148140680049781L;
	private Integer treatment_id;
	private String name; //quitar name!!!!!
	private String description;
	private Integer price;
	private Room room;
	private Clinician clinician; 
	private Patient patient;
	private List<Appointment> appointment;
	private List<Material> materials ; //quitar materials??
	

	public Treatment() {
		super();
		clinician = new Clinician();
		patient = new Patient();
		appointment = new ArrayList<Appointment>();
	}
	
	public Treatment(String name, String description, Integer price) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		
	}

	public Treatment(int treatmentId) {
		// TODO Auto-generated constructor stub
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	public Clinician getClinician() {
		return clinician;
	}

	public void setClinician(Clinician clinician) {
		this.clinician = clinician;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public List<Appointment> getAppointment() {
		return appointment;
	}

	public void setAppointment(List<Appointment> appointment) {
		this.appointment = appointment;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<Material> getMaterials() {
		return materials;
	}
	
	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}


	@Override
	public int hashCode() {
		return Objects.hash(appointment, clinician, description, name, price, room, treatment_id);
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
		return Objects.equals(appointment, other.appointment) && Objects.equals(clinician, other.clinician)
				&& Objects.equals(description, other.description) && Objects.equals(name, other.name)
				&& Objects.equals(price, other.price) && Objects.equals(room, other.room)
				&& Objects.equals(treatment_id, other.treatment_id);
	}


	@Override
	public String toString() {
		return "Treatment [name=" + name + ", description=" + description + ", price=" + price + ", room=" + room
				+ ", clinician=" + clinician + ", appointment=" + appointment + "]";
	}
}
