package dentalClinicIFaces;
import java.util.List;
import dentalClinicPOJOs.Clinician;

public interface ClinicianManager {
	
	    void addClinician(Clinician c);
	    List<Clinician> listClinicians();
	    Clinician getClinicianById(int id);
	    void deleteClinician(int id);
	    void updateClinician(Clinician c);
	}

