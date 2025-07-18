package dentalClinicJDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dentalClinicIFaces.MaterialManager;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Supplier;

public class JDBCMaterialManager implements MaterialManager {
    
	private JDBCManager manager;

    public JDBCMaterialManager(JDBCManager connectionManager) {
        this.manager = connectionManager;
    }

    @Override
    public void addMaterial(Material material) {
        String sql = "INSERT INTO Materials (supplier_id, name) VALUES (?, ?)";

        try {
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            ps.setInt(1, material.getSupplier().getSupplier_id());
            ps.setString(2, material.getName());
            
            ps.executeUpdate();
	        ps.close();

        } catch (SQLException e) {
            System.out.println("Error inserting material");
            e.printStackTrace();
        }
    }


    /*
    @Override
    public void linkMaterialToTreatment(int materialId, int treatmentId) {
        String sql = "INSERT INTO Treatment_materials (materials_id, treatment_id) VALUES (?, ?)";
        try {
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            ps.setInt(1, materialId);
            ps.setInt(2, treatmentId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error linking material to treatment");
            e.printStackTrace();
        }
    }
    */


    @Override
	public void deleteMaterial(Integer material_id) {
		
		String sql = "DELETE FROM Materials WHERE materials_id = ? "; 
		
		try {
			
			PreparedStatement ps = manager.getConnection().prepareStatement(sql) ; 
			
			ps.setInt(1, material_id); 
			ps.executeUpdate(); 
			ps.close(); 
			
		}catch(SQLException e) {
			e.printStackTrace(); 
			
		}
		
	}
    
    @Override
	public void updateMaterial(Integer material_id, String fieldName, String value) {
	    List<String> allowedFields = Arrays.asList("name");

	    if (!allowedFields.contains(fieldName)) {
	        throw new IllegalArgumentException("Invalid field name: " + fieldName);
	    }

	    String sql = "UPDATE Materials SET " + fieldName + " = ? WHERE materials_id = ?";

	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setObject(1, value);
	        ps.setInt(2, material_id);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public List<Material> getListOfTreatment_Materials(Integer treatment_id){
		List<Material> materials = new ArrayList<Material>();
        JDBCMaterialManager jdbcMaterialManager = new JDBCMaterialManager(manager);
        
		try {
			
			Statement stmt = manager.getConnection().createStatement();
			String sql = "SELECT materials_id FROM Treatment_materials WHERE treatment_id = " + treatment_id;
			ResultSet rs= stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				Integer material_id =rs.getInt("materials_id");
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
	
	@Override
	public List<Material> getListOfAllMaterials() {
	    List<Material> materials = new ArrayList<>();
	    JDBCMaterialManager jdbcMaterialManager = new JDBCMaterialManager(manager);

	    try {
	        Statement stmt = manager.getConnection().createStatement();
	        String sql = "SELECT materials_id FROM Materials"; 
	        ResultSet rs = stmt.executeQuery(sql);

	        while (rs.next()) {
	            Integer material_id = rs.getInt("materials_id");
	            Material material = jdbcMaterialManager.getMaterialByid(material_id);
	            materials.add(material);
	        }

	        rs.close();
	        stmt.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return materials;
	}

	
	@Override
	public List<Material> getListOfSupplier_Materials(Integer supplier_id) {
	    List<Material> materials = new ArrayList<>();
	  
	    try {
	        if (manager == null) {
	            throw new IllegalStateException("JDBCManager is null");
	        }

	        Statement stmt = manager.getConnection().createStatement();
	       
	        String sql = "SELECT Materials.materials_id, Materials.name, Suppliers.supplier_id, Suppliers.supplierName, Suppliers.phone, Suppliers.email FROM Materials JOIN Suppliers ON Materials.supplier_id = Suppliers.supplier_id WHERE Suppliers.supplier_id = " + supplier_id;
	        
	        ResultSet rs = stmt.executeQuery(sql);

	        while (rs.next()) {
	            
	            int materialId = rs.getInt("materials_id");
	            String materialName = rs.getString("name");

	            String supName = rs.getString("supplierName");
	            int phone = rs.getInt("phone");
	            String email = rs.getString("email");

	            Supplier supplier = new Supplier(supplier_id, supName, phone, email);
	            Material material = new Material(materialId, materialName, supplier);

	            materials.add(material);
	        }

	        rs.close();
	        stmt.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return materials;
	}

	@Override
	public Material getMaterialByid(Integer material_id) {
		Material material = null;
		JDBCSupplierManager jdbcSupplierManager = new JDBCSupplierManager(manager);
		
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql =  "SELECT name, supplier_id FROM Materials WHERE materials_id =" + material_id;
			
			ResultSet rs= stmt.executeQuery(sql);
			if(rs.next()) {
			    int supplier_id = rs.getInt("supplier_id");
			    Supplier supplier = jdbcSupplierManager.getSupplierByid(supplier_id);
			    String name = rs.getString("name");
			    material = new Material(name, supplier);
			    material.setMaterials_id(material_id);
			}
			rs.close();
			stmt.close();
			
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		return material;
		
	}

    
}