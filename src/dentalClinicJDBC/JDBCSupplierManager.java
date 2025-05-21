package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.SupplierManager;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Supplier;
import dentalClinicPOJOS.Treatment;

public class JDBCSupplierManager {
	

	    //private Connection c;
	    private JDBCManager manager;

	    public JDBCSupplierManager(JDBCManager connectionManager) {
	        this.manager = connectionManager;
	        //this.c = connectionManager.getConnection();
	    }
	    
	    public void addSupplier(Supplier supplier) {
	    	String sql = "INSERT INTO Suppliers ( supplierName , phone , email) VALUES (? , ? ,? ) "; 
	    	
	    	try {
	    		PreparedStatement ps = manager.getConnection().prepareStatement(sql); 
	    		ps.setString (1 , supplier.getSupplierName ()); 
	    		
	    		ps.setInt ( 2 , supplier.getPhone()); 
	    		ps.setString(3, supplier.getEmail()); 
	    		
	    		
	    		ps.executeUpdate(); 
	    		ps.close();
	    		
	    		
	    	}catch(SQLException e) {
	    		e.printStackTrace(); 
	    		
	    	}
	    	
	    }
		public void deleteSupplier(Integer supplier_id) {
			
			String sql = "DELETE FROM Suppliers WHERE id= ?"; 
			
			try {
				
				PreparedStatement ps = manager.getConnection().prepareStatement(sql); 
				ps.setInt(1, supplier_id); 
				
				ps.executeUpdate(); 
				ps.close() ; 
				
			} catch(SQLException e) {
				e.printStackTrace(); 
			}
			
		}
		public void updateSupplier(Integer supplier_id) {
			
			String sql = "UPDATE FROM Suppliers name = ? WHERE id = ?"; 
			
			try {
				
				PreparedStatement ps = manager.getConnection().prepareStatement (sql); 
				ps.setString ( 1 , "UpdatedName"); 
				ps.setInt ( 2 , supplier_id ); 
				
				ps.executeUpdate(); 
				ps.close(); 
			}catch(SQLException e) {
				
				e.printStackTrace(); 
				
			}
			
		}

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
				List<Material> materials = jdbcMaterialManager.getMaterialsOfSupplier(supplier_id);
				rs.close();
				stmt.close();
				
				supplier = new Supplier(supplierName, phone,email, materials);
			}catch(Exception e) 
			{
				e.printStackTrace();
			}
			
			return supplier;
			
		}
		
		public Supplier getSupplierOfMaterial(Integer material_id) {
			Supplier suppliers = null;
	        //JDBCSupplierManager jdbcSupplierManager = new JDBCSupplierManager(manager);
	        
			try {
				
				Statement stmt = manager.getConnection().createStatement();
	    		String sql = "SELECT * FROM Suppliers WHERE material_id = " + material_id;
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

	                List<Material> materials = jdbcMaterialManager.getMaterialsOfTreatment(supplier_id);

	                Supplier supplier = new Supplier(supplier_id, supplierName, phone, email);
	                supplier.setMaterial(materials);
	                
	                suppliers.add(supplier);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			return suppliers;
		}
	    
	   /*
	    //TODO tampoco estoy segura de que este bien
	    public void addSupplier(Supplier supplier) {
	        try {
	            String sql = "INSERT INTO Suppliers (name, surname, phone, email, address, material_id) VALUES (?, ?, ?, ?, ?, ?)";
	            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
	            ps.setString(1, supplier.getName());
	            ps.setString(2, supplier.getSurname());
	            ps.setInt(3, supplier.getPhone());
	            ps.setString(4, supplier.getEmail());
	            ps.setString(5, supplier.getAddress());
	            //ps.setInt(6, supplier.getMaterial().getId()); // Assuming Material has an ID
	            ps.executeUpdate();
	            ps.close();
	        } catch (SQLException e) {
	            System.out.println("Error inserting supplier");
	            e.printStackTrace();
	        }
	    }
	   
	    public List<Supplier> getAllSuppliers() {
	    	JDBCMaterialManager jdbcMaterialManager = new JDBCMaterialManager(conMan);
	        List<Supplier> suppliers = new ArrayList<>();
	        try {
	            String sql = "SELECT supplier_id, name, surname, phone, email, address, material_id FROM Suppliers";
	            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                int supplierId = rs.getInt("supplier_id");
	                String name = rs.getString("name");
	                String surname = rs.getString("surname");
	                int phone = rs.getInt("phone");
	                String email = rs.getString("email");
	                String address = rs.getString("address");
	                int materialId = rs.getInt("material_id");

	             
	                
	                List<Material> material = jdbcMaterialManager.getAllMaterialsById(materialId);

	                Supplier supplier = new Supplier(name, surname, phone, email, address, material);
	                supplier.setSupplier_id(supplierId);
	                suppliers.add(supplier);
	            }

	            rs.close();
	            ps.close();
	        } catch (SQLException e) {
	            System.out.println("Error retrieving suppliers");
	            e.printStackTrace();
	        }
	        return suppliers;
	    }

	    //TODO ns si esto esta bien
	    public void updateSupplier(Supplier supplier) {
	        try {
	            String sql = "UPDATE Suppliers SET name = ?, surname = ?, phone = ?, email = ?, address = ?, material_id = ? WHERE supplier_id = ?";
	            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
	            ps.setString(1, supplier.getName());
	            ps.setString(2, supplier.getSurname());
	            ps.setInt(3, supplier.getPhone());
	            ps.setString(4, supplier.getEmail());
	            ps.setString(5, supplier.getAddress());
	            //ps.setInt(6, supplier.getMaterial().getId());
	            ps.setInt(7, supplier.getSupplier_id());
	            ps.executeUpdate();
	            ps.close();
	        } catch (SQLException e) {
	            System.out.println("Error updating supplier");
	            e.printStackTrace();
	        }
	    }

	   
	    public void deleteSupplier(int supplierId) {
	        try {
	            String sql = "DELETE FROM Suppliers WHERE supplier_id = ?";
	            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
	            ps.setInt(1, supplierId);
	            ps.executeUpdate();
	            ps.close();
	        } catch (SQLException e) {
	            System.out.println("Error deleting supplier");
	            e.printStackTrace();
	        }
	    }

	    private Material getMaterialById(int materialId) {
	       
	        return new Material(materialId, null, materialId); 
	    }
	    */

	}

