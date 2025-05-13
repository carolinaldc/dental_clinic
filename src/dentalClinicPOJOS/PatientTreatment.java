package dentalClinicPOJOS;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class PatientTreatment implements Serializable {

    private static final long serialVersionUID = -2637321372475755L;

    private Integer patient_appointment_id;
    private Patient patient;
    private Treatment treatment;
    private Date date;
    private String comment;

    public PatientTreatment(String comment, Date date, Treatment treatment, Patient patient) {
        super();
        this.comment = comment;
        this.date = date;
        this.treatment = treatment;
        this.patient = patient;
    }

    public int getId() {
        return patient_appointment_id;
    }
    public void setId(Integer id) {
		this.patient_appointment_id = id;
	}
    

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    
    public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Treatment getTreatment() {
		return treatment;
	}

	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(comment, date, patient_appointment_id, patient, treatment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatientTreatment other = (PatientTreatment) obj;
		return Objects.equals(comment, other.comment) && Objects.equals(date, other.date)
				&& Objects.equals(patient_appointment_id, other.patient_appointment_id) && Objects.equals(patient, other.patient)
				&& Objects.equals(treatment, other.treatment);
	}

	@Override
	public String toString() {
		return "PatientTreatment [patient=" + patient + ", treatment=" + treatment + ", date=" + date + ", comment="
				+ comment + "]";
	}

	public String getAppointmentDate() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
