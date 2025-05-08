package dentalClinicIFaces;

<<<<<<< HEAD
import java.util.List;
import dentalClinicPOJOS.Patient;
=======
import dentalClinicPOJOS.Patient;

public interface PatientManager {
>>>>>>> branch 'master' of https://github.com/carolinaldc/dental_clinic.git

<<<<<<< HEAD
public interface PatientManager {
    void addPatient(Patient p);
    List<Patient> listPatients();
    Patient getPatientById(int id);
    void deletePatient(int id);
    void updatePatient(Patient p);
}
=======
	void getPatient(String email);
	void addPatient(Patient patient);
	  
    Patient getPatientById(int id);
    List<Patient> listPatients();
    void deletePatient(int id);
    void updatePatient(Patient patient);
}


>>>>>>> branch 'master' of https://github.com/carolinaldc/dental_clinic.git
