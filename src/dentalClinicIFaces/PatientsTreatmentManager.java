package dentalClinicIFaces;

import java.util.List;

import dentalClinicPOJOS.PatientTreatment;

public interface PatientsTreatmentManager {

	List<PatientTreatment> getPatientsTreatmentsByPatientid(int patientId);

	void addPatientTreatment(PatientTreatment newAppointment);

	void updatePatientTreatment(PatientTreatment appointmentToModify);

	PatientTreatment getPatientTreatmentById(int appointmentId);

	void removePatientTreatment(PatientTreatment appointmentToCancel);

	List<PatientTreatment> getAllPatientTreatments();
}
