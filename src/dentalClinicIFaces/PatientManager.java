package dentalClinicIFaces;


import java.util.List;
import dentalClinicPOJOS.Patient;


public interface PatientManager {
    List<Patient> listPatients();
    Patient getPatientById(int id);
    void deletePatient(int id);
    void updatePatient(Patient p);
	void getPatient(String email);
	void addPatient(Patient patient);	  
}
