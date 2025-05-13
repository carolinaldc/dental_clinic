package dentalClinicIFaces;


import java.util.List;
import dentalClinicPOJOS.Patient;


public interface PatientManager {
    List<Patient> listPatients();
    void deletePatient(int id);
    void updatePatient(Patient p);
	Patient getPatient(String email);
	void addPatient(Patient patient);
	Patient getPatientByid(int id);	  
}
