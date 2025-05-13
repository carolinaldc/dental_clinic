package dentalClinicJDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import dentalClinicPOJOS.Clinician;

public class JDBCPatientsClinicianManager implements PatientsClinicianManager {

	private JDBCManager manager;

	public JDBCPatientsClinicianManager(JDBCManager manager) {
		
		this.manager = manager;
	}
	
	@Override
    public List<Clinician> getPatientsCliniciansByPatientid(int id) {
    	
		Clinician clinician = null;
    	try {
    		Statement stmt = manager.getConnection().createStatement();
    		String sql = "SELECT * FROM Clinicians WHERE id=" + id;
			
			ResultSet rs= stmt.executeQuery(sql);
			
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			Integer phone = rs.getInt("telephone");
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
}
