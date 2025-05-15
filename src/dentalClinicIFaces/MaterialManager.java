package dentalClinicIFaces;

import java.util.List;

import dentalClinicPOJOS.Material;

public interface MaterialManager {

    void addMaterial(Material material);

    List<Material> getAllMaterials();

    void updateMaterial(Material material);

    void deleteMaterial(int materialId);

    void linkMaterialToTreatment(int materialId, int treatmentId);

	List<Material> getAllMaterialsById(int id);

}
