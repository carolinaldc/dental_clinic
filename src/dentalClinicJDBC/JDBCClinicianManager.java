package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.ClinicianManager;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Patient;

public class JDBCClinicianManager implements ClinicianManager {

    private JDBCManager manager;

    public JDBCClinicianManager(JDBCManager manager) {
        this.manager = manager;
    }
    
    public void addClinician(Clinician clinician) {
    	
    	String sql = "INSERT INTO Clinician (specialty, phone , id , name , email , surname) VALUES (? , ?, ?, ? ,? ,?)"; 
    try {
    	PreparedStatement ps = manager.getConnection().prepareStatement(sql); 
    	
    	ps.setString(1, clinician.getSpeciality()); 
    	ps.setInt(2,clinician.getPhone() ); 
    	ps.setInt (3, clinician.getClinician_id()); 
    	ps.setString(4, clinician.getName()); 
    	ps.setString (5, clinician.getEmail()); 
    	ps.setString(6, clinician.getSurname());
    	
    	ps.executeUpdate(); 
    	ps.close ();     	
    	
    	} catch(SQLException e) {
    		
    		e.printStackTrace(); 
    		
    	}
    	
    }
	public void deleteClinician(Integer clinician_id) {
		
		String sql = "DELETE FROM Clinician WHERE id=? "; 
		
		try {
			PreparedStatement ps = manager.getConnection().prepareStatement(sql); 
			ps.setInt (1, clinician_id); 
			
			ps.executeUpdate(); 
			ps.close (); 
		}catch (SQLException e) {
			
			e.printStackTrace(); 
		}
		
	}
   	public void updateClinician(Integer clinician_id) {
   		
   		String sql = "UPDATE Clinican SET speciality = ? , phone =? , name = ? , id= ? , surname = ? , email = ?"; 
   		
   		try {
   			
   			PreparedStatement ps = manager.getConnection().prepareStatement(sql); 
   			
   		  ps.setString(1, "New Specialty");
          ps.setInt(2, 123456789);
          ps.setString(3, "UpdatedName");
          ps.setString(4, "updated@example.com");
          ps.setString(5, "UpdatedSurname");
          ps.setInt(6, clinician_id);
          
          ps.executeUpdate();
          ps.close();
   		}catch (SQLException e) {
   	        e.printStackTrace();
   		}
   	}
   	
	public Clinician getClinicianByid(Integer clinician_id) {
		
		Clinician clinician = null;
		manager = new JDBCManager();
		JDBCAppointmentManager jdbcAppointmentManager = new JDBCAppointmentManager(manager);
		
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql =  "SELECT * FROM Clinicians WHERE clinician_id =" + clinician_id;
			
			ResultSet rs= stmt.executeQuery(sql);
			
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String speciality = rs.getString("speciality");
			Integer phone = rs.getInt("phone");
			String email = rs.getString("email");
			List<Appointment> appointments = jdbcAppointmentManager.getAppointmentOfClinician(clinician_id);
			rs.close();
			stmt.close();
			
			clinician = new Clinician(name, surname,speciality, phone, email, appointments);
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		return clinician;
	}
	
	public List<Clinician> getListOfClinicians(){
		return null;
	}
   
/*
    @Override
    public void addClinician(Clinician clinician) {
        String sql = "INSERT INTO clinicians (name, specialty) VALUES (?, ?)";

        try (PreparedStatement stmt = manager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, clinician.getName());
            stmt.setString(2, clinician.getSpeciality());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al añadir dentista: " + e.getMessage());
        }
    }

    @Override
    public List<Clinician> listClinicians() {
        List<Clinician> clinicians = new ArrayList<>();
        String sql = "SELECT * FROM clinicians";

        try (Statement stmt = manager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Clinician c = new Clinician(
                    rs.getInt("clinician_id"),
                    rs.getString("name"),
                    rs.getString("specialty")
                );
                clinicians.add(c);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los dentistas: " + e.getMessage());
        }

        return clinicians;
    }

    

	//()@Override
	//public void getClinician(String email) {
		// TODO Auto-generated method stub
		
	//}
	
	 //He creado este metodo para hacer lo de katerina de XML
	@Override
    public Clinician getClinicianByid(int id) {
    	
		Clinician clinician = null;
    	try {
    		Statement stmt = manager.getConnection().createStatement();
    		String sql = "SELECT * FROM Clinicians WHERE clinician_id=" + id;
			
			ResultSet rs= stmt.executeQuery(sql);
			
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			Integer phone = rs.getInt("phone");
			String email = rs.getString("email");
			String specialty = rs.getString("specialty");
            
			
			rs.close();
			stmt.close();
			
			clinician = new Clinician(name, surname, specialty, email, phone);
    	}catch(Exception e) 
		{
			e.printStackTrace();
		}
    	return clinician;
    }
	
	@Override
	public Clinician getClinicianByEmail(String email) {
	    Clinician clinician = null;
	    try {
	        String sql = "SELECT * FROM Clinicians WHERE email = ?";
	        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	        stmt.setString(1, email);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            String name = rs.getString("name");
	            String surname = rs.getString("surname");
	            String specialty = rs.getString("specialty");
	            Integer phone = rs.getInt("phone"); 
	            String emailResult = rs.getString("email");

	            clinician = new Clinician(name, surname, specialty, emailResult, phone);
	        }

	        rs.close();
	        stmt.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return clinician;
	}


	
    @Override
    public Clinician getClinicianById(int id) {
        try (PreparedStatement ps = manager.getConnection().prepareStatement(
        		"SELECT * FROM clinicians WHERE clinician_id = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Clinician(
                	rs.getInt("clinician_id"),
                    rs.getString("name"),
                    rs.getString("specialty")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

	
    @Override
    public void deleteClinician(int id) {
        try (PreparedStatement ps = manager.getConnection().prepareStatement(
        		"DELETE FROM clinicians WHERE clinician_id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClinician(Clinician cl) {
        try (PreparedStatement ps = manager.getConnection().prepareStatement(
        		"UPDATE clinicians SET name=?, specialty=? WHERE clinician_id=?")) {
            ps.setString(1, cl.getName());
            ps.setString(2, cl.getSpeciality());
            ps.setInt(3, cl.getClinicianId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    */
        
}


