package dentalClinicIFaces;

import java.util.List;

import dentalClinicPOJOS.PatientTreatment;

public interface PatientsTreatmentManager {

	List<PatientTreatment> getPatientsTreatmentsByPatientid(int patientId);
}
