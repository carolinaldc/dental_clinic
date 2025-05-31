package dentalClinicJDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.TreatmentMaterialsManager;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Treatment;

public class JDBCTreatmentMaterialsManager implements TreatmentMaterialsManager {
	
	private JDBCManager manager;

    public JDBCTreatmentMaterialsManager(JDBCManager manager) {
        this.manager = manager;
    }
    
    @Override
    public void linkMaterialToTreatment(int materialId, int treatmentId) {
        String sql = "INSERT INTO Treatment_materials (materials_id, treatment_id) VALUES (?, ?)";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, materialId);
            ps.setInt(2, treatmentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error linking material to treatment");
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Material> getMaterialsForTreatment(int treatmentId) {
        List<Material> materials = new ArrayList<>();
        JDBCMaterialManager materialManager = new JDBCMaterialManager(manager);

        String sql = "SELECT materials_id FROM Treatment_materials WHERE treatment_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, treatmentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int materialId = rs.getInt("materials_id");
                Material material = materialManager.getMaterialByid(materialId);
                if (material != null) {
                    materials.add(material);
                }
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving materials for treatment");
            e.printStackTrace();
        }

        return materials;
    }
    
    @Override
    public List<Treatment> getTreatmentsForMaterial(int materialId) {
        List<Treatment> treatments = new ArrayList<>();
        JDBCTreatmentManager treatmentManager = new JDBCTreatmentManager(manager);

        String sql = "SELECT treatment_id FROM Treatment_materials WHERE materials_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, materialId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int treatmentId = rs.getInt("treatment_id");
                Treatment treatment = treatmentManager.getTreatmentById(treatmentId);
                if (treatment != null) {
                    treatments.add(treatment);
                }
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving treatments for material");
            e.printStackTrace();
        }

        return treatments;
    }
    
    @Override
    public void unlinkMaterialFromTreatment(int materialId, int treatmentId) {
        String sql = "DELETE FROM Treatment_materials WHERE materials_id = ? AND treatment_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, materialId);
            ps.setInt(2, treatmentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error unlinking material from treatment");
            e.printStackTrace();
        }
    }
    
    


}
