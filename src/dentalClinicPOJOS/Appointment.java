package dentalClinicPOJOS;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Appointment implements Serializable {

    private static final long serialVersionUID = -2637321372475755L;

    private Integer appointment_id;
    private Date date;
    private String comment;
    private Patient patient;
    private Treatment treatment;
    private Clinician clinician;
    
    
	public Appointment() {
		super();
	}

	

	//TODO: appointment_id
	public Appointment(Date date, String comment, Patient patient, Treatment treatment,
			Clinician clinician) {
		super();
		this.appointment_id = appointment_id;
		this.date = date;
		this.comment = comment;
		this.patient = patient;
		this.treatment = treatment;
		this.clinician = clinician;
	}



	public Integer getAppointment_id() {
		return appointment_id;
	}


	public void setAppointment_id(Integer appointment_id) {
		this.appointment_id = appointment_id;
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


	public Clinician getClinician() {
		return clinician;
	}


	public void setClinician(Clinician clinician) {
		this.clinician = clinician;
	}


	@Override
	public int hashCode() {
		return Objects.hash(appointment_id, clinician, comment, date, patient, treatment);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		return Objects.equals(appointment_id, other.appointment_id) && Objects.equals(clinician, other.clinician)
				&& Objects.equals(comment, other.comment) && Objects.equals(date, other.date)
				&& Objects.equals(patient, other.patient) && Objects.equals(treatment, other.treatment);
	}


	//TODO: appointment_id
	@Override
	public String toString() {
		return "Appointment [appointment_id=" + appointment_id + ", date=" + date + ", comment=" + comment
				+ ", patient=" + patient + ", treatment=" + treatment + ", clinician=" + clinician + "]";
	}
}
