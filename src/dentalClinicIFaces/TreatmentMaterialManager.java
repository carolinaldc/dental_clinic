package dentalClinicIFaces;


import java.util.List;

import dentalClinicPOJOS.TreatmentMaterial;
import dentalClinicPOJOS.Treatment;
import dentalClinicPOJOS.Material;


public interface TreatmentMaterialManager {


	   void addMaterialToTreatment(int treatmentId, int materialId);
	   List<Material> getMaterialsForTreatment(int treatmentId);
	   boolean isMaterialAssignedToTreatment(int treatmentId, int materialId);
	   void updateMaterialQuantityInTreatment(int treatmentId, int materialId, int quantity);
	   List<Treatment> getTreatmentsForMaterial(int materialId);
	}


