package dentalClinicIFaces;


import java.util.List;
import dentalClinicPOJOS.Clinician;



public interface ClinicianManager {
	
	void addClinician(Clinician clinician);
	List<Clinician> listClinicians();
	Clinician getClinicianByid(int id);
   	Clinician getClinicianById(int id);
   	void deleteClinician(int id);
   	void updateClinician(Clinician cl);
   	//List<Clinician> getAllClinicians(); TODOvoid updateClinician(Clinician cl);
	//Clinician getClinicianByEmail(String email); TODO

}

