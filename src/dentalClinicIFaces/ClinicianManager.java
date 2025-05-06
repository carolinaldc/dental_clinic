package dentalClinicIFaces;

import dentalClinicPOJOS.Clinician;

public interface ClinicianManager {
	void getClinician(String email);
	void addClinician(Clinician clinician);
}
