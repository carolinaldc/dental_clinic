package dentalClinicJDBC;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.MaterialManager;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.Supplier;
import dentalClinicPOJOS.Treatment;

public class JDBCMaterialManager implements MaterialManager {
    
	//private Connection c;
	private JDBCManager manager;

    public JDBCMaterialManager(JDBCManager connectionManager) {
        this.manager = connectionManager;
        //this.c = connectionManager.getConnection();
    }

    public void addMaterial(Material material) {
    	
    	String sql = " INSERT INTO Material (id , supplier , name) VALUES id= ? , name= ? , supplier = ? "; 
    	
    	try {
    		
    		PreparedStatement ps = manager.getConnection().prepareStatement(sql); 
    		
    		ps.setInt(1, material.getMaterials_id()); 
    		ps.setInt(2, material.getSupplier().getSupplier_id());
    		ps.setString(3, material.getName()); 
    		
    		ps.executeUpdate(); 
    		ps.close(); 
    		
    		
    		
  
    	} catch(SQLException e) {
    		
    		e.printStackTrace(); 
    	}
    	
    }
	public void deleteMaterial(Integer material_id) {
		
		String sql = "DELETE FROM Materials WHERE id = ? "; 
		
		try {
			
			PreparedStatement ps = manager.getConnection().prepareStatement(sql) ; 
			
			ps.setInt(1, material_id); 
			ps.executeUpdate(); 
			ps.close(); 
			
		}catch(SQLException e) {
			e.printStackTrace(); 
			
		}
		
	}
	public void updateMaterial(Integer material_id) {
		
		String sql = "UPDATE Material SET name = ? WHERE materials_id= ?"; 
		
		try {
			
			PreparedStatement ps = manager.getConnection().prepareStatement(sql); 
			
			ps.setString (1, "UpdatedName"); 
			ps.setInt (2 , material_id); 
			ps.executeUpdate () ; 
			ps.close (); 
			
		}catch(SQLException e) {
			
			e.printStackTrace(); 
		}
		
	}
	
	public List<Material> getMaterialsOfTreatment(Integer treatment_id){
		List<Material> materials = new ArrayList<Material>();
        JDBCSupplierManager jdbcSupplierManager = new JDBCSupplierManager(manager);
        JDBCMaterialManager jdbcMaterialManager = new JDBCMaterialManager(manager);
        
		try {
			
			Statement stmt = manager.getConnection().createStatement();
    		String sql = "SELECT material_id FROM Treatment_materials WHERE treatment_id = " + treatment_id;
			ResultSet rs= stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				Integer material_id =rs.getInt("material_id");
				Material material =  jdbcMaterialManager.getMaterialByid(material_id);
	            materials.add(material);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		return materials;
	}
	
	public List<Material> getMaterialsOfSupplier(Integer supplier_id){
		List<Material> materials = new ArrayList<Material>();
        JDBCSupplierManager jdbcSupplierManager = new JDBCSupplierManager(manager);
        
		try {
			
			Statement stmt = manager.getConnection().createStatement();
    		String sql = "SELECT * FROM Materials WHERE supplier_id = " + supplier_id;
			ResultSet rs= stmt.executeQuery(sql);
			
			while(rs.next()) {
	            String name = rs.getString("comment");
	            
	            //EN ESTE CASO NO HACE FALTA TENER TREATMENT EN LOS MATERIALES NO?
	            //List<Treatment> treatments = jdbcTreatmentManager.getTreatmentById(treatment_id);
	            Supplier supplier = jdbcSupplierManager.getSupplierByid(supplier_id);
	            
	            
	            Material material = new Material(name, supplier);
	            materials.add(material);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		return materials;
	}

	public Material getMaterialByid(Integer material_id) {
		Material material = null;
		manager = new JDBCManager();
		JDBCSupplierManager jdbcSupplierManager = new JDBCSupplierManager(manager);
		
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql =  "SELECT name, supplier FROM Materials WHERE material_id =" + material_id;
			
			ResultSet rs= stmt.executeQuery(sql);
			
			String name = rs.getString("name");
			Supplier supplier = jdbcSupplierManager.getSupplierOfMaterial(material_id);
			rs.close();
			stmt.close();
			
			material = new Material(name, supplier);
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		return material;
		
	}
	public List<Material> getListOfMaterials(){
		List<Material> materials = new ArrayList<Material>(); 
		JDBCAppointmentManager jdbcAppointmentManager = new JDBCAppointmentManager(manager);
		
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "Select * FROM materials";
			ResultSet rs = stmt.executeQuery(sql);
			JDBCTreatmentManager jdbcTreatmentManager = new JDBCTreatmentManager(manager);
			JDBCSupplierManager jdbcSupplierManager = new JDBCSupplierManager(manager);
			
			while(rs.next())
			{
				Integer materials_id= rs.getInt("materials_id");
				String name= rs.getString("name");
				Integer supplier_id = rs.getInt("supplier_id");
				Integer treatment_id = rs.getInt("treatment_id");
				
				Supplier supplier = jdbcSupplierManager.getSupplierByid(supplier_id);
				Treatment treatment = jdbcTreatmentManager.getTreatmentById(treatment_id);
				
				
				List<Appointment> appointments = jdbcAppointmentManager.getAppointmentOfPatient(materials_id);
				
				
				
				//TODO
				
				Material m= new Material(materials_id, name, supplier_id, treatment_id);
				materials.add(m);
				
				//////////
				
			}

			rs.close();
			stmt.close();
			
		}catch(Exception e) {
			e.printStackTrace();}
		
		return materials;
	}
	
    /*
    @Override 
    public void addMaterial(Material material) {
        try {
            String sql = "INSERT INTO materials (name, stock) VALUES (?, ?)";
            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
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

            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
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
    public List<Material> getAllMaterialsById(int idTreatment) {
        List<Material> materials = new ArrayList<>();
        try {
           
            String sql = "SELECT * FROM materials WHERE treatment_id =" + idTreatment;
            

            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
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
            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
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
            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
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
            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
            ps.setInt(1, treatmentId);
            ps.setInt(2, materialId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error linking material to treatment");
            e.printStackTrace();
        }
    }
*/
    
}
