package dentalClinicIFaces;

<<<<<<< HEAD
import java.util.List;
=======
import dentalClinicPOJOS.Clinician;
>>>>>>> branch 'master' of https://github.com/carolinaldc/dental_clinic.git

<<<<<<< HEAD
import dentalClinicPOJOS.Clinician;

public interface ClinicianManager {
	
	    void addClinician(Clinician clinician);
	    List<Clinician> getAllClinicians();
	    Clinician getClinicianById(int id);
	    void deleteClinician(int id);
	    void updateClinician(Clinician clinician);
	}
=======
public interface ClinicianManager {
	void getClinician(String email);
	 void addClinician(Clinician clinician);
   	 Clinician getClinicianById(int id);
   	 List<Clinician> getAllClinicians();
    	void deleteClinician(int id);
    	void updateClinician(Clinician cl);
}
>>>>>>> branch 'master' of https://github.com/carolinaldc/dental_clinic.git

