package dentalClinicPOJOS;
import java.io.Serializable; 
import java.sql.Date; 
import java.util.Objects;

public class Patients_Clinician implements Serializable{

	private static final long serialVersionUID = 7404145314772486159L;
	private Patient id_patient ;  
	private Clinician id_clinician ;  
	private Date date ;  
	private String visitInfo ;  
	private int id ;
	
	public Patients_Clinician() {
		super();
	}

	public Patient getId_patient() {
		return id_patient;
	}

	public void setId_patient(Patient id_patient) {
		this.id_patient = id_patient;
	}

	public Clinician getId_clinician() {
		return id_clinician;
	}

	public void setId_clinician(Clinician id_clinician) {
		this.id_clinician = id_clinician;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getVisitInfo() {
		return visitInfo;
	}

	public void setVisitInfo(String visitInfo) {
		this.visitInfo = visitInfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, id, id_clinician, id_patient, visitInfo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patients_Clinician other = (Patients_Clinician) obj;
		return Objects.equals(date, other.date) && id == other.id && Objects.equals(id_clinician, other.id_clinician)
				&& Objects.equals(id_patient, other.id_patient) && Objects.equals(visitInfo, other.visitInfo);
	}

	@Override
	public String toString() {
		return "Patients_Clinician [date=" + date + ", visitInfo=" + visitInfo + "]";
	}
	
	
	
	
	
}
