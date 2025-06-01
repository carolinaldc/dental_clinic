package dentalClinicIFaces;


import java.util.List;

import dentalClinicPOJOS.Treatment;


public interface TreatmentManager {
	
	public void addTreatment(Treatment treatment);
	public void deleteTreatment (Integer treatment_id);
	public void updateTreatment(Treatment treatment);
	public List <Treatment> getListOfTreatments();
	public Treatment getTreatmentById(Integer treatment_id); 
}