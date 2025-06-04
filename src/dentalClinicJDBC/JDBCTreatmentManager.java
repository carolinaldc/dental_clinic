package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.TreatmentManager;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Treatment;

public class JDBCTreatmentManager implements TreatmentManager {

    private JDBCManager manager;

    public JDBCTreatmentManager(JDBCManager connectionManager) {
        this.manager = connectionManager;
    }
    
    
    @Override
    public void addTreatment(Treatment treatment) {
    	String sql = "INSERT INTO Treatments (name, description, price) VALUES (?, ?, ?)";
        ResultSet generatedKeys = null;

        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, treatment.getName());
            ps.setString(2, treatment.getDescription());
            ps.setInt(3, treatment.getPrice());
            ps.executeUpdate();

            generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int treatmentId = generatedKeys.getInt(1);
                treatment.setTreatment_id(treatmentId);  // Save for future use

                // Now link materials
                JDBCTreatmentMaterialsManager treatmentMaterialsManager = new JDBCTreatmentMaterialsManager(manager);
                for (Material material : treatment.getMaterials()) {
                    treatmentMaterialsManager.linkMaterialToTreatment(material.getMaterials_id(), treatmentId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        
    }

    
	public void deleteTreatment (Integer treatment_id) {
		String sql = "DELETE FROM Treatments WHERE treatment_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, treatment_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	@Override
	public void updateTreatment(Treatment treatment) {
		
		String updatesql = "UPDATE Treatments SET name = ?, description = ?, price = ? WHERE treatment_id = ?";
		String deleteMaterialsSQL = "DELETE FROM Treatment_Materials WHERE treatment_id = ?";
	    String insertMaterialSQL = "INSERT INTO Treatment_Materials (treatment_id, materials_id) VALUES (?, ?)";
		
	    try {
	        PreparedStatement ps = manager.getConnection().prepareStatement(updatesql);

	        ps.setString(1, treatment.getName());
	        ps.setString(2, treatment.getDescription());
	        ps.setInt(3, treatment.getPrice());
	        ps.setInt(4, treatment.getTreatment_id());

	        ps.executeUpdate();
	        ps.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    try {
	    	PreparedStatement ps = manager.getConnection().prepareStatement(deleteMaterialsSQL);
	    	ps.setInt(1, treatment.getTreatment_id());
            ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } 
	    
	    try {
	    	PreparedStatement ps = manager.getConnection().prepareStatement(insertMaterialSQL);
	    	
	    	for (Material material : treatment.getMaterials()) {
                ps.setInt(1, treatment.getTreatment_id());
                ps.setInt(2, material.getMaterials_id());
                ps.addBatch(); // Batch insertion
            }
            ps.executeBatch();
	    	
	    	
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } 
	    
	    
	}
	
	public List <Treatment> getListOfTreatments(){
		 List<Treatment> treatments = new ArrayList<>();
	        JDBCAppointmentManager jdbcAppointmentManager = new JDBCAppointmentManager(manager);
	        JDBCMaterialManager jdbcMaterialManager = new JDBCMaterialManager(manager);

	        String sql = "SELECT * FROM Treatments";
	        try (Statement stmt = manager.getConnection().createStatement();
	             ResultSet rs = stmt.executeQuery(sql)) {

	            while (rs.next()) {
	                Integer id = rs.getInt("treatment_id");
	                String name = rs.getString("name");
	                String description = rs.getString("description");
	                Integer price = rs.getInt("price");

	                List<Appointment> appointments = jdbcAppointmentManager.getAppointmentOfTreatments(id);
	                List<Material> materials = jdbcMaterialManager.getListOfTreatment_Materials(id);

	                Treatment treatment = new Treatment(name, description, price, appointments, materials);
	                treatment.setTreatment_id(id);
	                treatments.add(treatment);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return treatments;
	}
	
	
	public Treatment getTreatmentById(Integer treatment_id) {
	    Treatment treatment = null;
	    JDBCMaterialManager jdbcMaterialManager = new JDBCMaterialManager(manager);

	    try {
	        Statement stmt = manager.getConnection().createStatement();
	        String sql = "SELECT * FROM Treatments WHERE treatment_id = " + treatment_id;
	        ResultSet rs = stmt.executeQuery(sql);

	        if (rs.next()) {
	            String name = rs.getString("name");
	            String description = rs.getString("description");
	            Integer price = rs.getInt("price");

	            List<Appointment> appointments = new ArrayList<>();
	            List<Material> materials = jdbcMaterialManager.getListOfTreatment_Materials(treatment_id);

	            treatment = new Treatment(name, description, price, appointments, materials);
	            treatment.setTreatment_id(treatment_id);
	        }

	        rs.close();
	        stmt.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return treatment;
	}

}