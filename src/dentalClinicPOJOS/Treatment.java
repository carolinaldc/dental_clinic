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
	private Room room;
	private List<Clinician> clinician; 
	private List<PatientTreatment> appointment;
	

	public Treatment() {
		super();
		clinician = new ArrayList<Clinician>();
		appointment = new ArrayList<PatientTreatment>();
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	public List<Clinician> getClinician() {
		return clinician;
	}

	public void setClinician(List<Clinician> clinician) {
		this.clinician = clinician;
	}

	public List<PatientTreatment> getAppointment() {
		return appointment;
	}

	public void setAppointment(List<PatientTreatment> appointment) {
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
