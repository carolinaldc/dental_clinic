package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dentalClinicIFaces.ClinicianManager;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Clinician;

public class JDBCClinicianManager implements ClinicianManager {

    private JDBCManager manager;

    public JDBCClinicianManager(JDBCManager manager) {
        this.manager = manager;
    }
    
    @Override
    public void addClinician(Clinician clinician) {
    	String sql = "INSERT INTO Clinicians (name, surname, specialty, phone, email) VALUES (?, ?, ?, ?, ?)";
    	

    try {
    	PreparedStatement ps = manager.getConnection().prepareStatement(sql); 
    	
    	ps.setString(1, clinician.getName());
    	ps.setString(2, clinician.getSurname());
    	ps.setString(3, clinician.getSpecialty());
    	ps.setInt(4, clinician.getPhone());
    	ps.setString(5, clinician.getEmail());
    	
    	ps.executeUpdate(); 
    	ps.close ();     	
    	
    	} catch(SQLException e) {
    		
    		e.printStackTrace(); 
    		
    	}
    	
    }
    
    @Override
	public void deleteClinician(Integer clinician_id) {
		
		String sql = "DELETE FROM Clinicians WHERE clinician_id=? "; 
		
		try {
			PreparedStatement ps = manager.getConnection().prepareStatement(sql); 
			ps.setInt (1, clinician_id); 
			
			ps.executeUpdate(); 
			ps.close (); 
		}catch (SQLException e) {
			
			e.printStackTrace(); 
		}
		
	}
	
	@Override
	public void deleteClinicianByEmail(String email) {
		String sql = "DELETE FROM Clinicians WHERE email = ?";
		
        try {
            
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ps.executeUpdate();
            ps.close();
            System.out.println("Clinician record deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete clinician.");
        }
    }
	
	@Override
	public void updateClinician(Integer clinician_id, String fieldName, Object value) {
	    List<String> allowedFields = Arrays.asList("name", "surname", "specialty", "phone");

	    if (!allowedFields.contains(fieldName)) {
	        throw new IllegalArgumentException("Invalid field name: " + fieldName);
	    }

	    String sql = "UPDATE Clinicians SET " + fieldName + " = ? WHERE clinician_id = ?";

	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setObject(1, value);
	        ps.setInt(2, clinician_id);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	@Override
	public Clinician getClinicianByEmail(String email) {
	    Clinician clinician = null;
	    JDBCAppointmentManager jdbcAppointmentManager = new JDBCAppointmentManager(manager);

	    String sql = "SELECT * FROM Clinicians WHERE email = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setString(1, email);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            Integer clinician_id = rs.getInt("clinician_id");
	            String name = rs.getString("name");
	            String surname = rs.getString("surname");
	            String specialty = rs.getString("specialty");
	            Integer phone = rs.getInt("phone");

	            //List<Appointment> appointments = jdbcAppointmentManager.getAppointmentOfClinician(clinician_id);

	            clinician = new Clinician(clinician_id, name, surname, specialty, phone, email);
	            
	        }
	        rs.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return clinician;
	}
   	
	@Override
	public Clinician getClinicianById(Integer clinician_id) {
	    Clinician clinician = null;
	    JDBCAppointmentManager jdbcAppointmentManager = new JDBCAppointmentManager(manager);

	    try {
	        Statement stmt = manager.getConnection().createStatement();
	        String sql =  "SELECT * FROM Clinicians WHERE clinician_id =" + clinician_id;

	        ResultSet rs = stmt.executeQuery(sql);

	        if (rs.next()) {
	            String name = rs.getString("name");
	            String surname = rs.getString("surname");
	            String specialty = rs.getString("specialty");
	            Integer phone = rs.getInt("phone");
	            String email = rs.getString("email");

	            //List<Appointment> appointments = jdbcAppointmentManager.getAppointmentOfClinician(clinician_id);
	            
	            clinician = new Clinician(clinician_id, name, surname, specialty, phone, email);
	            
	        }

	        rs.close();
	        stmt.close();

	    } catch(Exception e) {
	        e.printStackTrace();
	    }

	    return clinician;
	}

	@Override
	public List<Clinician> getListOfClinicians(){
		List<Clinician> clinicians = new ArrayList<Clinician>();
		JDBCAppointmentManager jdbcAppointmentManager = new JDBCAppointmentManager(manager);
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "Select * FROM clinicians";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				Integer clinician_id = rs.getInt("clinician_id");
				String name= rs.getString("name");
				String surname = rs.getString("surname");
				String specialty = rs.getString("specialty");
				String email = rs.getString("email");
				Integer phone = rs.getInt("phone");
				
				List<Appointment> appointments = jdbcAppointmentManager.getAppointmentOfClinician(clinician_id);
				
				Clinician c= new Clinician(clinician_id, name, surname, specialty, phone, email, appointments);
				clinicians.add(c);
			}

			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return clinicians;
	}
	
	@Override
	public void updateClinicianEmail(int clinicianId, String newEmail) {
	    String sql = "UPDATE Clinicians SET email = ? WHERE clinician_id = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setString(1, newEmail);
	        ps.setInt(2, clinicianId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void updateName(int clincianId, String name) {
	    String sql = "UPDATE Clinicians SET name = ? WHERE clinician_id = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setString(1, name);
	        ps.setInt(2, clincianId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void updateSurname(int clincianId, String surname) {
	    String sql = "UPDATE Clinicians SET surname = ? WHERE clinician_id = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setString(1, surname);
	        ps.setInt(2, clincianId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void updatePhone(int clincianId, Integer phone) {
	    String sql = "UPDATE Clinicians SET phone = ? WHERE clinician_id = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setInt(1, phone);
	        ps.setInt(2, clincianId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void updateSpecialty(int clincianId, String specialty) {
	    String sql = "UPDATE Clinicians SET specialty = ? WHERE clinician_id = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setString(1, specialty);
	        ps.setInt(2, clincianId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
        
}


