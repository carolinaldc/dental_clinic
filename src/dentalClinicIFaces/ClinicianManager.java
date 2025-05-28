package dentalClinicIFaces;


import java.util.List;

import dentalClinicPOJOS.Clinician;



public interface ClinicianManager {
	
	public void addClinician(Clinician clinician);
	public void deleteClinician(Integer clinician_id);
	public void updateClinician(Integer clinician_id, String fieldName, Object value);
   	
	public Clinician getClinicianById(Integer clinician_id);
	public Clinician getClinicianByEmail(String email);
	public List<Clinician> getListOfClinicians();
	void deleteClinicianByEmail(String email);
}

