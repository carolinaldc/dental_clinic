package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.PatientManager;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.Treatment;
import dentalClinicPOJOS.Appointment;

public class JDBCPatientManager implements PatientManager {
    private JDBCManager manager;

    public JDBCPatientManager(JDBCManager manager) {
        this.manager = manager;
    }

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
    
   
	public void deletePatient (Integer patient_id) {
		
		String sql = "DELETE FROM Patients WHERE id=? "; 
		
		try {
			
			PreparedStatement ps = manager.getConnection().prepareStatement(sql); 
			ps.setInt ( 1, patient_id); 
			
			ps.executeUpdate (); 
			ps.close (); 
		}catch(SQLException e) {
			
			e.printStackTrace(); 
			
		}
		
	}
	
	public void updatePatient(Integer patient_id) {
		
		String sql = "UPDATE FROM Patients name = ? WHERE id = ?" ;
				
				try {
					
					PreparedStatement ps = manager.getConnection().prepareStatement(sql); 
					
					ps.setString(1, "UpdatedName"); 
					ps.setInt (2, patient_id); 
					
					ps.executeUpdate(); 
					ps.close(); 
					
					
				}catch(SQLException e) {
					
					e.printStackTrace(); 
					
				}	
	}
	
	
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
	
	
	public Patient getPatientById(Integer patient_id){
		
		Patient patient = null;
		manager = new JDBCManager();
		JDBCAppointmentManager jdbcAppointmentManager = new JDBCAppointmentManager(manager);
		JDBCMaterialManager jdbcMaterialManager = new JDBCMaterialManager(manager);
		
		try {
			
			Statement stmt = manager.getConnection().createStatement();
			String sql =  "SELECT * FROM Patients WHERE patient_id =" + patient_id;
			
			ResultSet rs= stmt.executeQuery(sql);
			
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			Date dob = rs.getDate("dob");
			Integer phone = rs.getInt("phone");
			String email = rs.getString("email");
			Integer credit_card = rs.getInt("credit_card");
			List<Appointment> appointments = jdbcAppointmentManager.getAppointmentOfPatient(patient_id);
			
			rs.close();
			stmt.close();
			
			patient = new Patient(name, surname,dob, phone, email, credit_card, appointments);
			
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		return patient;
	
	}
    
    /*
    @Override
    public void addPatient(Patient p) {
        String sql = "INSERT INTO patients (name, surname, dob, phone, email, credit_card) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
        	ps.setString(1, p.getName());
            ps.setString(2, p.getSurname());
            ps.setDate(3, p.getDob());
            ps.setInt(4, p.getPhone());
            ps.setString(5, p.getEmail());
            ps.setInt(6, p.getCredit_card());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    //HE creado uno nuevo paracido a lo que hizo katerina en XMLManager y decidimos que queremos
    @Override
    public Patient getPatientByid(int id) {
    	JDBCPatientsClinicianManager jdbcPatientsCliniciansManager = new JDBCPatientsClinicianManager(manager);
    	JDBCAppointmentManager jdbcPatientsTreatmentsManager = new JDBCAppointmentManager(manager);
    	Patient patient = null;
    	try {
    		Statement stmt = manager.getConnection().createStatement();
    		String sql =  "SELECT * FROM patients WHERE patient_id=" + id;
			
			ResultSet rs= stmt.executeQuery(sql);
			
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			Date date = rs.getDate("dob"); 
			Integer phone = rs.getInt("phone");
			Integer credit_card = rs.getInt("credit_card");
			String email = rs.getString("email");
			
			//DO IT FOR PATIENTCLINICIANS
            //int clinician_id = rs.getInt("clinician_id");
            //Clinician clinician = jdbcClinicianManager.getClinicianByid(clinician_id);
            rs.close();
			stmt.close();
			
			List<Patients_Clinician> patientsClinicians = jdbcPatientsCliniciansManager.getPatientsCliniciansByPatientid(id);
			List<Appointment> patientstreatments = jdbcPatientsTreatmentsManager.getPatientsTreatmentsByPatientid(id);
            patient = new Patient(name, surname, date, phone, email, credit_card, patientstreatments, patientsClinicians);
            
	        
	        
	
			
			patient = new Patient(name, surname, date, phone, email, credit_card,patientstreatments,patientsClinicians);
    	}catch(Exception e) 
		{
			e.printStackTrace();
		}
    	return patient;
    }
    
    
    //@Override
    //public Patient getPatientById(int id) {
    //   String sql = "SELECT * FROM patients WHERE id = ?";
    //   try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
    //      ps.setInt(1, id);
    //      ResultSet rs = ps.executeQuery();
    //      if (rs.next()) {
    //          return new Patient(
    //             rs.getString("name"),
    //               rs.getString("surname"),
    //              rs.getDate("birth_date"),
    //               rs.getInt("phone"),
    //               rs.getString("mail"),
    //               rs.getInt("credit_card")
    //           );
    //       }
    //  } catch (SQLException e) {
    //       e.printStackTrace();
    //   }
    //   return null;
    //}

    @Override
    public List<Patient> listPatients() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Statement st = manager.getConnection().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Patient p = new Patient(
                		rs.getString("name"),
                        rs.getString("surname"),
                        rs.getDate("dob"),
                        rs.getInt("phone"),
                        rs.getString("email"),
                        rs.getInt("credit_card")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deletePatient(int id) {
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePatient(Patient p) {
        String sql = "UPDATE patients SET name = ?, surname = ?, dob = ?, phonw = ? , email = ?, credit_card = ?, WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
        	ps.setString(1, p.getName());
            ps.setString(2, p.getSurname());
            ps.setDate(3, p.getDob());
            ps.setInt(4, p.getPhone());
            ps.setString(5, p.getEmail());
            ps.setInt(6, p.getCredit_card());
            ps.setInt(8,p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Patient getPatient(String email) {
        String sql = "SELECT * FROM patients WHERE email = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Patient p = new Patient(
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getDate("dob"),
                    rs.getInt("phone"),
                    rs.getString("email"),
                    rs.getInt("credit_card")
                );
                p.setId(rs.getInt("patient_id")); 
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    */

}
