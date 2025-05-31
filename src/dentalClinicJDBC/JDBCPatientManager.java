package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dentalClinicIFaces.PatientManager;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.Appointment;

public class JDBCPatientManager implements PatientManager {
    private JDBCManager manager;

    public JDBCPatientManager(JDBCManager manager) {
        this.manager = manager;
    }

    @Override
    public void addPatient(Patient patient) {
    	
    	String sql = " INSERT INTO Patients (name , surname , dob , phone  , email, credit_card) VALUES (? , ? , ?, ?, ?, ? ) "; 
    	
    	try {
    		
    		PreparedStatement ps = manager.getConnection().prepareStatement(sql); 
    		
    		ps.setString ( 1, patient.getName()); 
    		ps.setString ( 2, patient.getSurname()); 
    		ps.setDate ( 3, patient.getDob()); 
    		ps.setInt (4, patient.getPhone()); 
    		ps.setString(5, patient.getEmail()); 
    		ps.setInt(6, patient.getCredit_card()); 
    		
    		ps.executeUpdate (); 
    		ps.close() ; 
    		
    	}catch(SQLException e ) {
    		e.printStackTrace(); 
    		
    	}
    }
    
   
    @Override
	public void deletePatient (Integer patient_id) {
		
		String sql = "DELETE FROM Patients WHERE patient_id=? ";  
		
		try {
			
			PreparedStatement ps = manager.getConnection().prepareStatement(sql); 
			ps.setInt ( 1, patient_id); 
			
			ps.executeUpdate (); 
			ps.close (); 
		}catch(SQLException e) {
			
			e.printStackTrace(); 
			
		}
		
	}
	
	@Override
	public void deletePatientByEmail(String email) {
		String sql = "DELETE FROM Patients WHERE email = ?";
		
        try {
            
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ps.executeUpdate();
            ps.close();
            System.out.println("Patient record deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete patient.");
        }
    }
	
	

	@Override
	public List <Patient> getListOfPatients(){
		List<Patient> patients = new ArrayList<Patient>();
		JDBCAppointmentManager jdbcAppointmentManager = new JDBCAppointmentManager(manager);
		try {
			Statement stmt = manager.getConnection().createStatement();
			String sql = "Select * FROM Patients";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				Integer patient_id= rs.getInt("patient_id");
				String name= rs.getString("name");
				String surname = rs.getString("surname");
				Date dob = rs.getDate("dob");
				Integer phone = rs.getInt("phone");
				String email = rs.getString("email");
				Integer credit_card = rs.getInt("credit_card");
				
				
				List<Appointment> appointments = jdbcAppointmentManager.getAppointmentOfPatient(patient_id);
				
				Patient p= new Patient(patient_id, name, surname, dob, phone, email, credit_card, appointments);
				patients.add(p);
				
			}

			rs.close();
			stmt.close();
			
		}catch(Exception e) {
			e.printStackTrace();}
		
		return patients;
	}
	
	@Override
	public Patient getPatientById(Integer patient_id) {
	    Patient patient = null;
	    //JDBCAppointmentManager jdbcAppointmentManager = new JDBCAppointmentManager(manager);

	    try {
	        Statement stmt = manager.getConnection().createStatement();
	        String sql = "SELECT * FROM Patients WHERE patient_id = " + patient_id;

	        ResultSet rs = stmt.executeQuery(sql);
	        if (rs.next()) {
	            String name = rs.getString("name");
	            String surname = rs.getString("surname");
	            Date dob = rs.getDate("dob");
	            Integer phone = rs.getInt("phone");
	            String email = rs.getString("email");
	            Integer credit_card = rs.getInt("credit_card");

	            //List<Appointment> appointments = jdbcAppointmentManager.getAppointmentOfClinician(patient_id);
	            
	            patient = new Patient(patient_id, name, surname, dob, phone, email, credit_card);
	            
	        }

	        rs.close();
	        stmt.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return patient;
	}


	@Override
	public void updatePatientEmail(int patientId, String newEmail) {
	    String sql = "UPDATE Patients SET email = ? WHERE patient_id = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setString(1, newEmail);
	        ps.setInt(2, patientId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void updatePatient(Integer patient_id, String fieldName, Object value) {
		List<String> allowedFields = Arrays.asList("name", "surname", "dob", "phone", "credit_card");

	    if (!allowedFields.contains(fieldName)) {
	        throw new IllegalArgumentException("Invalid field name: " + fieldName);
	    }

	    String sql = "UPDATE Patients SET " + fieldName + " = ? WHERE patient_id = ?";

	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setObject(1, value);
	        ps.setInt(2, patient_id);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
    
	@Override
	public Patient getPatientByEmail(String email) {
	    Patient patient = null;
	    //JDBCAppointmentManager jdbcAppointmentManager = new JDBCAppointmentManager(manager);

	    String sql = "SELECT * FROM Patients WHERE email = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setString(1, email);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            Integer patient_id = rs.getInt("patient_id");
	            String name = rs.getString("name");
	            String surname = rs.getString("surname");
	            Date dob = rs.getDate("dob");
	            Integer phone = rs.getInt("phone");
	            Integer credit_card = rs.getInt("credit_card");

	            //List<Appointment> appointments = jdbcAppointmentManager.getAppointmentOfPatient(patient_id);

	            patient = new Patient(patient_id, name, surname, dob, phone, email, credit_card);
	        }
	        rs.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return patient;
	}
	
	@Override
	public void updateName(int patientId, String name) {
	    String sql = "UPDATE Patients SET name = ? WHERE patient_id = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setString(1, name);
	        ps.setInt(2, patientId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void updateSurname(int patientId, String surname) {
	    String sql = "UPDATE Patients SET surname = ? WHERE patient_id = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setString(1, surname);
	        ps.setInt(2, patientId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void updatePhone(int patientId, Integer phone) {
	    String sql = "UPDATE Patients SET phone = ? WHERE patient_id = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setInt(1, phone);
	        ps.setInt(2, patientId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void updateCreditCard(int patientId, Integer credit_card) {
	    String sql = "UPDATE Patients SET credit_card = ? WHERE patient_id = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setInt(1, credit_card);
	        ps.setInt(2, patientId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
}
