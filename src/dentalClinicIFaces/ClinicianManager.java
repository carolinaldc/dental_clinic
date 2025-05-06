package dentalClinicIFaces;

import dentalClinicPOJOS.Clinician;

public interface ClinicianManager {
	void getClinician(String email);
	 void addClinician(Clinician clinician);
   	 Clinician getClinicianById(int id);
   	 List<Clinician> getAllClinicians();
    	void deleteClinician(int id);
    	void updateClinician(Clinician cl);
}

