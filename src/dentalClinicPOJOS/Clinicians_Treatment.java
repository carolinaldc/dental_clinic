package dentalClinicPOJOS;

import java.io.Serializable;
import java.util.Objects;

public class Clinicians_Treatment implements Serializable {

	private static final long serialVersionUID = 370799687574270796L;
	private Integer clinician_appointment_id;
	private Treatment treatment;
	private Clinician clinician;
	
	public Clinicians_Treatment(Clinician clinician, Treatment treatment) {
		super();
		this.clinician = clinician;
		this.treatment = treatment;	
	}

	public Integer getClinician_appointment_id() {
		return clinician_appointment_id;
	}

	public void setClinician_appointment_id(Integer clinician_appointment_id) {
		this.clinician_appointment_id = clinician_appointment_id;
	}

	public Treatment getTreatment() {
		return treatment;
	}

	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}

	public Clinician getClinician() {
		return clinician;
	}

	public void setClinician(Clinician clinician) {
		this.clinician = clinician;
	}

	@Override
	public int hashCode() {
		return Objects.hash(clinician, clinician_appointment_id, treatment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Clinicians_Treatment other = (Clinicians_Treatment) obj;
		return Objects.equals(clinician, other.clinician)
				&& Objects.equals(clinician_appointment_id, other.clinician_appointment_id)
				&& Objects.equals(treatment, other.treatment);
	}

	@Override
	public String toString() {
		return "Clinicians_Treatment [treatment=" + treatment + ", clinician=" + clinician + "]";
	}
	
	
	
	

	
}
