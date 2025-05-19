package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.SupplierManager;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Supplier;

public class JDBCSupplierManager {
	

	    //private Connection c;
	    private JDBCManager conMan;

	    public JDBCSupplierManager(JDBCManager connectionManager) {
	        this.conMan = connectionManager;
	        //this.c = connectionManager.getConnection();
	    }
	    
	    public void addSupplier(Supplier supplier) {
	    	
	    }
		public void deleteSupplier(Integer supplier_id) {
			
		}
		public void updateSupplier(Integer supplier_id) {
			
		}

		public Supplier getSupplierByid(Integer supplier_id) {
			return null;
		}
		public List<Supplier> getListOfSuppliers(){
			return null;
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

