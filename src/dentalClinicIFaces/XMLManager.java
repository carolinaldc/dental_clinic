package dentalClinicIFaces;

import java.io.File;

import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Material;

public interface XMLManager {

	public void clinician2xml(Integer id);

	public Clinician xml2Clinician(File xml);
	
	public void material2xml(Integer id);
	
	public Material xml2Material(File xml);

}
