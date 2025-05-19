package dentalClinicIFaces;


import java.util.List;

import dentalClinicPOJOS.Patient;


public interface PatientManager {
    
	public void addPatient(Patient patient);
	public void deletePatient (Integer patient_id);
	public void updatePatient(Integer patient_id);
	public List <Patient> getListOfPatients();
	public Patient getPatientById(Integer patient_id); 

}
