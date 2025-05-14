package dentalClinicIFaces;

import java.util.List;
import dentalClinicPOJOS.Patients_Clinician;

public interface PatientsClinicianManager {

	
    void addPatientClinician(Patients_Clinician pc);

    List<Patients_Clinician> getAllPatientsClinicians();

    
    Patients_Clinician getPatientClinicianById(int id);

    
    void updatePatientClinician(Patients_Clinician pc);

   
    void deletePatientClinician(int id);
	
	
}
