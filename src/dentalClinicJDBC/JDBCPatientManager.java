package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.PatientManager;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Patient;

public class JDBCPatientManager implements PatientManager {
    private JDBCManager manager;

    public JDBCPatientManager(JDBCManager manager) {
        this.manager = manager;
    }

    @Override
    public void addPatient(Patient p) {
        String sql = "INSERT INTO patients (name, surname, birth_date, phone, email, credit_card, urgency) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
    	JDBCClinicianManager jdbcClinicianManager = new JDBCClinicianManager(manager);
    	Patient patient = null;
    	try {
    		Statement stmt = manager.getConnection().createStatement();
			String sql =  "SELECT * FROM Patients WHERE id=" + id;
			
			ResultSet rs= stmt.executeQuery(sql);
			
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			Date date = rs.getDate("dob");
			Integer phone = rs.getInt("telephone");
			Integer credit_card = rs.getInt("credit_card");
			String email = rs.getString("email");
			
			//DO IT FOR PATIENTCLINICIANS
            //int clinician_id = rs.getInt("clinician_id");
            //Clinician clinician = jdbcClinicianManager.getClinicianByid(clinician_id);
            rs.close();
			stmt.close();
            
	        
	        
	
			
			patient = new Patient(name, surname, date, phone, email, credit_card, clinician);
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
                        rs.getDate("birth_date"),
                        rs.getInt("phone"),
                        rs.getString("mail"),
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
        String sql = "DELETE FROM patients WHERE id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePatient(Patient p) {
        String sql = "UPDATE patients SET name = ?, surname = ?, birth_date = ?, phonw = ? , email = ?, credit_card = ?, urgency = ?, WHERE id = ?";
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
                return new Patient(
                		rs.getString("name"),
                        rs.getString("surname"),
                        rs.getDate("birth_date"),
                        rs.getInt("phone"),
                        rs.getString("email"),
                        rs.getInt("credit_card")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
