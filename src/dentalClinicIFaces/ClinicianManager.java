package dentalClinicIFaces;

import java.util.List;

import dentalClinicPOJOS.Clinician;

public interface ClinicianManager {
	
	    void addClinician(Clinician clinician);
	    List<Clinician> getAllClinicians();
	    Clinician getClinicianById(int id);
	    void deleteClinician(int id);
	    void updateClinician(Clinician clinician);
	}

