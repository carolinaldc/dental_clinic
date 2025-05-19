package dentalClinicIFaces;


import java.util.List;

import dentalClinicPOJOS.Clinician;



public interface ClinicianManager {
	
	public void addClinician(Clinician clinician);
	public void deleteClinician(Integer clinician_id);
   	public void updateClinician(Integer clinician_id);
   	
	public Clinician getClinicianByid(Integer clinician_id);
	public List<Clinician> getListOfClinicians();
}

