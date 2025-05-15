package dentalClinicIFaces;


import java.util.List;

import dentalClinicPOJOS.Treatment;

public interface TreatmentManager {
    void addTreatment(Treatment treatment);
    List<Treatment> getAllTreatments();
    void updateTreatment(Treatment treatment);
    void deleteTreatment(int treatmentId);
	Treatment getTreatmentByid(int id);
}