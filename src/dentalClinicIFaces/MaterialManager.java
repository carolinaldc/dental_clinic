package dentalClinicIFaces;

import java.util.List;

import dentalClinicPOJOS.Material;

public interface MaterialManager {

	public void addMaterial(Material material);
	public void deleteMaterial(Integer material_id);
	public void updateMaterial(Integer material_id);

	public Material getMaterialByid(Integer material_id);
	public List<Material> getListOfMaterials();
	public List<Material> getMaterialsOfTreatment(Integer treatment_id);
	
    //void linkMaterialToTreatment(int materialId, int treatmentId);
}
