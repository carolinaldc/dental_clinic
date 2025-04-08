package dentalClinicPOJOS;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Clinicians_treatment implements Serializable {

	private static final long serialVersionUID = 1L;
	private Treatment treatment_id;  
	private Clinician id_clinician ;   
	private int idappointment_id ;
	
	public Clinicians_treatment() {
		super();
	}

	public Treatment getTreatment_id() {
		return treatment_id;
	}

	public void setTreatment_id(Treatment treatment_id) {
		this.treatment_id = treatment_id;
	}

	public Clinician getId_clinician() {
		return id_clinician;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_clinician, idappointment_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Clinicians_treatment other = (Clinicians_treatment) obj;
		return Objects.equals(id_clinician, other.id_clinician) && idappointment_id == other.idappointment_id;
	}

	@Override
	public String toString() {
		return "Clinicians_treatment [id_clinician=" + id_clinician + ", idappointment_id=" + idappointment_id + "]";
	}

	
}
