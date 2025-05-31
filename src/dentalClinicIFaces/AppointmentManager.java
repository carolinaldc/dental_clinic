package dentalClinicIFaces;

import java.sql.Date;
import java.util.List;

import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Role;

public interface AppointmentManager {

	public void addAppointment(Appointment o);
	public void deleteAppointment (Integer appointment_id);
	//public void updateAppointment(Integer appointment_id, Date newDate, Integer patient_id, Integer treatment_id, Integer clinician_id);
	public List <Appointment> getListOfAppointments(String email, Role role);
	public List <Appointment> getAppointmentOfPatient (Integer patient_id);
	public List <Appointment> getAppointmentOfClinician (Integer clinician_id);
	public List <Appointment> getAppointmentOfTreatments (Integer treatment_id);
	//public Appointment getAppointmentById (Integer appointment_id);
	Appointment getAppointmentById(Integer appointment_id);
	void updateAppointment(Appointment appt);
	void updateAppointmentDate(int appointmentId, Date newDate);
	void updateAppointmentComments(int appointmentId, String comments);
	void updateAppointmentPatient(int appointmentId, int patientId);
	void updateAppointmentTreatment(int appointmentId, int treatmentId);
	void updateAppointmentClinician(int appointmentId, int clinicianId);
}
