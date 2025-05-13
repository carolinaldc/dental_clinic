package dentalClinicIFaces;

import java.io.File;

import dentalClinicPOJOS.Clinician;

public interface XMLManager {

	void patient2xml(Integer id);

	Clinician xml2Clinician(File xml);

}
