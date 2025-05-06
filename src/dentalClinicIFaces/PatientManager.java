package dentalClinicIFaces;

import java.util.List;
import dentalClinicPOJOS.Patient;

public interface PatientManager {
    void addPatient(Patient p);
    List<Patient> listPatients();
    Patient getPatientById(int id);
    void deletePatient(int id);
    void updatePatient(Patient p);
}