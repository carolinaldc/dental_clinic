package dentalClinicIFaces;


import java.util.List;

import dentalClinicPOJOS.Patient;


public interface PatientManager {
    
	public void addPatient(Patient patient);
	public void deletePatient (Integer patient_id);
	public void deletePatientByEmail(String email);
	public void updatePatient(Integer patient_id, String fieldName, Object value);
	
	public List <Patient> getListOfPatients();
	public Patient getPatientById(Integer patient_id); 
	public Patient getPatientByEmail(String email);
	void updatePatientEmail(int patientId, String newEmail);
	void updateName(int patientId, String name);
	void updateSurname(int patientId, String surname);
	void updatePhone(int patientId, Integer phone);
	void updateCreditCard(int patientId, Integer credit_card);
	

}
