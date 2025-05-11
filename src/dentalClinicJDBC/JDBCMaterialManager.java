package dentalClinicJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.MaterialManager;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Supplier;
import dentalClinicPOJOS.Treatment;

public class JDBCMaterialManager implements MaterialManager {
    private Connection c;
    private JDBCManager conMan;

    public JDBCMaterialManager(JDBCManager connectionManager) {
        this.setConMan(connectionManager);
        this.c = connectionManager.getConnection();
    }

    @Override 
    public void addMaterial(Material material) {
        try {
            String sql = "INSERT INTO materials (name, stock) VALUES (?, ?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, material.getName());
            ps.setInt(2, material.getStock());
            ps.executeUpdate();
            ps.close();
            
  
        } catch (SQLException e) {
            System.out.println("Error inserting material");
            e.printStackTrace();
        }
    }

    @Override
    public List<Material> getAllMaterials() {
        List<Material> materials = new ArrayList<>();
        try {
           
            String sql = "SELECT material_id, name, stock FROM materials";

            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                
                int id = rs.getInt("material_id");  
                String name = rs.getString("name");
                int stock = rs.getInt("stock");

                
                Material material = new Material(id, name, stock);
                materials.add(material);
            }
            
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving materials");
            e.printStackTrace();
        }
        return materials;
    }


    @Override
    public void updateMaterial(Material material) {
        try {
            String sql = "UPDATE materials SET material_name = ?, quantity = ?, supplier_id = ? WHERE material_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, material.getName());
            ps.setInt(2, material.getStock());
           
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error updating material");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMaterial(int materialId) {
        try {
            String sql = "DELETE FROM materials WHERE material_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, materialId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error deleting material");
            e.printStackTrace();
        }
    }

    @Override
    public void linkMaterialToTreatment(int materialId, int treatmentId) {
        try {
            String sql = "INSERT INTO treatment_material (treatment_id, material_id) VALUES (?, ?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, treatmentId);
            ps.setInt(2, materialId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error linking material to treatment");
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
