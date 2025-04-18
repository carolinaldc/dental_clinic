package dentalClinicJDBC;

import dentalClinicPOJOS.Patient;
import java.sql.PreparedStatement;

import dentalClinicIfaces.PatientManager;

public class JDBCPatientManager implements PatientManager{
	private JDBCPatientManager manager;
	
	private JDBCPatientManager(JDBCManager m) {
		this.manager = m;
	}
	
	public void addPatient(Patient p) {
		try {
			String sql = "INSERT INTO Patients (name, surname, dob, creditcard, email, telephone, urgency, id_clinician) VALUES(?,?,?,?,?,?,?,?)";
			
			PreparedStatement prep = manager.getConnection().prepareStatement(sql);
			
			prep.setString(1, p.getName());
			prep.setString(2, p.getSurname());
			prep.setDate(3, p.getDob());
			prep.setInt(4, p.getCredit_card());
			prep.setString(5, p.getEmail());
			prep.setInt(6, p.getPhone());
			prep.setEmergency(7, p.getUrgency());
			prep.setInt(8, p.getClinician_id);
			
			prep.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
