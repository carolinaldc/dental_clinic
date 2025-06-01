package dentalClinicIFaces;

import java.util.List;

import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Treatment;

public interface TreatmentMaterialsManager {
    
	void linkMaterialToTreatment(int materialId, int treatmentId);

	List<Material> getMaterialsForTreatment(int treatmentId);

	List<Treatment> getTreatmentsForMaterial(int materialId);

	void unlinkMaterialFromTreatment(int materialId, int treatmentId);
            
}
