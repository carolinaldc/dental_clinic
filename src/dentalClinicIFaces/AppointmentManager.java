package dentalClinicIFaces;

import java.util.List;

import dentalClinicPOJOS.Appointment;

public interface AppointmentManager {

	List<Appointment> getPatientsTreatmentsByPatientid(int patientId);
	
	void addPatientTreatment(Appointment newAppointment);
	void updatePatientTreatment(Appointment appointmentToModify);
	Appointment getPatientTreatmentById(int appointmentId);
	void removePatientTreatment(Appointment appointmentToCancel);
	List<Appointment> getAllPatientTreatments();
}
