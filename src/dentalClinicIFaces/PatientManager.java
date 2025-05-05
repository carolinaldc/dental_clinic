package dentalClinicIFaces;

import dentalClinicPOJOS.Patient;

public interface PatientManager {

	void getPatient(String email);
	void addPatient(Patient patient);

}
