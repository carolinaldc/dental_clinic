package dentalClinicIFaces;


import java.util.List;
import dentalClinicPOJOS.Clinician;



public interface ClinicianManager {
	void getClinician(String email);
	void addClinician(Clinician clinician);
   	Clinician getClinicianById(int id);
   	//List<Clinician> getAllClinicians(); TODO
    void deleteClinician(int id);
    void updateClinician(Clinician cl);
	List<Clinician> listClinicians();
}

