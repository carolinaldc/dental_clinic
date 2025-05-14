package dentalClinicJDBC;
package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalClinicPOJOS.TreatmentMaterial;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Treatment;

public class JDBCTreatmentMaterialManager {

    private Connection c;
    private JDBCManager conMan;

    public JDBCTreatmentMaterialManager(JDBCManager connectionManager) {
        this.conMan = connectionManager;
        this.c = connectionManager.getConnection();
    }
    



    public void addTreatmentMaterial(TreatmentMaterial tm) {
        try {
            String sql = "INSERT INTO Treatment_materials (treatment_id, materials_id, description, tools) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, tm.getTreatment().getTreatment_id());
            ps.setInt(2, tm.getMaterial().getId());
            ps.setString(3, tm.getDescription());
            ps.setString(4, tm.getTools());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error inserting treatment material");
            e.printStackTrace();
        }
    }

    public List<TreatmentMaterial> getAllTreatmentMaterials() {
        List<TreatmentMaterial> materials = new ArrayList<>();
        try {
            String sql = "SELECT id, treatment_id, materials_id, description, tools FROM Treatment_materials";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int treatmentId = rs.getInt("treatment_id");
                int materialId = rs.getInt("materials_id");
                String description = rs.getString("description");
                String tools = rs.getString("tools");

                Treatment treatment = new Treatment();
                treatment.setTreatment_id(treatmentId);
                Material material = new Material(materialId, null, null);    

                TreatmentMaterial tm = new TreatmentMaterial(material, treatment, tools, description);
                tm.setId(id);
                materials.add(tm);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving treatment materials");
            e.printStackTrace();
        }
        return materials;
    }

    public void updateTreatmentMaterial(TreatmentMaterial tm) {
        try {
            String sql = "UPDATE Treatment_materials SET treatment_id = ?, materials_id = ?, description = ?, tools = ? WHERE id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, tm.getTreatment().getTreatment_id());
            ps.setInt(2, tm.getMaterial().getMaterials_id());
            ps.setString(3, tm.getDescription());
            ps.setString(4, tm.getTools());
            ps.setInt(5, tm.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error updating treatment material");
            e.printStackTrace();
        }
    }

    public void deleteTreatmentMaterial(int id) {
        try {
            String sql = "DELETE FROM Treatment_materials WHERE id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error deleting treatment material");
            e.printStackTrace();
        }
    }

    public JDBCManager getConMan() {
        return conMan;
    }

    public void setConMan(JDBCManager conMan) {
        this.conMan = conMan;
    }
}
