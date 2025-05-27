package dentalClinicIFaces;

import java.util.List;

import dentalClinicPOJOS.Material;

public interface MaterialManager {

	public void addMaterial(Material material);
	public void deleteMaterial(Integer material_id);
	public void updateMaterial(Integer material_id, String fieldName, String value);

	public Material getMaterialByid(Integer material_id);
	public List<Material> getListOfSupplier_Materials(Integer supplier_id);
	public List<Material> getListOfTreatment_Materials(Integer treatment_id);
	public List<Material> getListOfAllMaterials();
	
	public void linkMaterialToTreatment(int materialId, int treatmentId);
}
