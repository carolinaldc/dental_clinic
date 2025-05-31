package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dentalClinicIFaces.SupplierManager;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Supplier;

public class JDBCSupplierManager implements SupplierManager {
	

	    private JDBCManager manager;

	    public JDBCSupplierManager(JDBCManager connectionManager) {
	        this.manager = connectionManager;
	    }
	    
	    @Override
	    public void addSupplier(Supplier supplier) {
	    	String sql = "INSERT INTO Suppliers (supplierName , phone , email) VALUES (? , ? ,? ) "; 
	    	
	    	try {
	    		PreparedStatement ps = manager.getConnection().prepareStatement(sql); 
	    		ps.setString (1 ,supplier.getSupplierName ()); 
	    		ps.setInt ( 2 , supplier.getPhone()); 
	    		ps.setString(3, supplier.getEmail()); 
	    		
	    		ps.executeUpdate(); 
	    		ps.close();
	    	}catch(SQLException e) {
	    		e.printStackTrace(); 
	    		
	    	}
	    	
	    }
	    
	    @Override
		public void deleteSupplier(Integer supplier_id) {
			
			String sql = "DELETE FROM Suppliers WHERE supplier_id= ?"; 
			
			try {
				
				PreparedStatement ps = manager.getConnection().prepareStatement(sql); 
				ps.setInt(1, supplier_id); 
				
				ps.executeUpdate(); 
				ps.close() ; 
				
			} catch(SQLException e) {
				e.printStackTrace(); 
			}
		}
		
		@Override
		public void deleteSupplierByEmail(String email) {
			String sql = "DELETE FROM Suppliers WHERE email = ?";
			
	        try {
	            
	            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
	            ps.setString(1, email);
	            ps.executeUpdate();
	            ps.close();
	            System.out.println("Supplier record deleted.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Failed to delete supplier.");
	        }
	    }
		
		@Override
		public void updateSupplier(Integer supplier_id) {
			
			String sql = "UPDATE FROM Suppliers supplierName = ? WHERE supplier_id = ?"; 
			
			try {
				
				PreparedStatement ps = manager.getConnection().prepareStatement (sql); 
				ps.setString (1, "UpdatedName"); 
				ps.setInt (2, supplier_id ); 
				
				ps.executeUpdate(); 
				ps.close(); 
			}catch(SQLException e) {
				
				e.printStackTrace(); 
				
			}
			
		}

		@Override
		public void updateSupplier(Integer supplier_id, String fieldName, Object value) {
		    List<String> allowedFields = Arrays.asList("supplierName", "phone", "materials");

		    if (!allowedFields.contains(fieldName)) {
		        throw new IllegalArgumentException("Invalid field supplierName: " + fieldName);
		    }

		    String sql = "UPDATE Suppliers SET " + fieldName + " = ? WHERE supplier_id = ?";

		    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
		        ps.setObject(1, value);
		        ps.setInt(2, supplier_id);
		        ps.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}

		
		@Override
		public Supplier getSupplierByid(Integer supplier_id) {
			
			Supplier supplier = null;
			manager = new JDBCManager();
			JDBCMaterialManager jdbcMaterialManager = new JDBCMaterialManager(manager);
			
			try {
				Statement stmt = manager.getConnection().createStatement();
				String sql =  "SELECT * FROM Suppliers WHERE supplier_id =" + supplier_id;
				
				ResultSet rs= stmt.executeQuery(sql);
				
				String supplierName = rs.getString("supplierName");
				Integer phone = rs.getInt("phone");
				String email = rs.getString("email");
				List<Material> materials = jdbcMaterialManager.getListOfSupplier_Materials(supplier_id);
				rs.close();
				stmt.close();
				
				supplier = new Supplier(supplierName, phone, email, materials);
			}catch(Exception e) 
			{
				e.printStackTrace();
			}
			
			return supplier;
			
		}
		
		@Override
		public Supplier getSupplierByEmail(String email) {
		    Supplier supplier = null;
		    JDBCMaterialManager jdbcMaterialManager = new JDBCMaterialManager(manager);

		    String sql = "SELECT * FROM Suppliers WHERE email = ?";
		    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
		        ps.setString(1, email);
		        ResultSet rs = ps.executeQuery();

		        if (rs.next()) {
		            Integer supplier_id = rs.getInt("supplier_id");
		            String supplierName = rs.getString("supplierName");
		            Integer phone = rs.getInt("phone");

		            List<Material> materials = jdbcMaterialManager.getListOfSupplier_Materials(supplier_id);

		            supplier = new Supplier(supplier_id, supplierName, phone, email, materials);
		        }
		        rs.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return supplier;
		}

		@Override
		public Supplier getSupplierOfMaterial(Integer material_id) {
			Supplier suppliers = null;
	        
			try {
				
				
				Statement stmt = manager.getConnection().createStatement();
	    		String sql = "SELECT * FROM Materials m JOIN Suppliers s ON m.supplier_id = s.supplier_id WHERE m.material_id = " + material_id;
				ResultSet rs= stmt.executeQuery(sql);
				
				
				Integer supplier_id = rs.getInt("supplier_id");
		        String supplierName = rs.getString("supplierName");
		        Integer phone = rs.getInt("phone");
		        String email = rs.getString("email");
		            
		         Supplier supplier = new Supplier(supplier_id, supplierName, phone, email );
		           
				
				
				rs.close();
				stmt.close();
				
			}catch(Exception e) 
			{
				e.printStackTrace();
			}
			return suppliers;
		}
		
		@Override
		public List<Supplier> getListOfSuppliers(){
			List<Supplier> suppliers = new ArrayList<>();
	        JDBCMaterialManager jdbcMaterialManager = new JDBCMaterialManager(manager);

	        String sql = "SELECT * FROM Suppliers";
	        try (Statement stmt = manager.getConnection().createStatement();
	             ResultSet rs = stmt.executeQuery(sql)) {

	            while (rs.next()) {
	                Integer supplier_id = rs.getInt("supplier_id");
	                String supplierName = rs.getString("supplierName");
	                Integer phone = rs.getInt("phone");
	                String email = rs.getString("email");

	                List<Material> materials = jdbcMaterialManager.getListOfSupplier_Materials(supplier_id);

	                Supplier supplier = new Supplier(supplier_id, supplierName, phone, email);
	                supplier.setMaterial(materials);
	                
	                suppliers.add(supplier);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			return suppliers;
		}
		
		@Override
		public void updateSupplierEmail(int supplierId, String newEmail) {
		    String sql = "UPDATE Suppliers SET email = ? WHERE supplier_id = ?";
		    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
		        ps.setString(1, newEmail);
		        ps.setInt(2, supplierId);
		        ps.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		
		@Override
		public void updateName(int supplierId, String name) {
		    String sql = "UPDATE Suppliers SET supplierName = ? WHERE supplier_id = ?";
		    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
		        ps.setString(1, name);
		        ps.setInt(2, supplierId);
		        ps.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		
		@Override
		public void updatePhone(int supplierId, Integer phone) {
		    String sql = "UPDATE Suppliers SET phone = ? WHERE supplier_id = ?";
		    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
		        ps.setInt(1, phone);
		        ps.setInt(2, supplierId);
		        ps.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	    

	}