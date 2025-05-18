package dentalClinicIFaces;

import java.io.File;

import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Patients_Clinician;

public interface XMLManager {

	void patient2xml(Integer id);

	Patients_Clinician xml2PatientsClinician(File xml);

}
