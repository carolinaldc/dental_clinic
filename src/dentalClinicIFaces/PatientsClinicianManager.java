package dentalClinicIFaces;

import java.util.List;

import dentalClinicPOJOS.Patients_Clinician;

public interface PatientsClinicianManager {
	
	List<Patients_Clinician> getPatientsCliniciansByPatientid(int patientId);

}
