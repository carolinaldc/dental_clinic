package dentalClinicJDBC;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.PatientsTreatmentManager;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.PatientTreatment;
import dentalClinicPOJOS.Treatment;

public class JDBCPatientTreatmentManager implements PatientsTreatmentManager {
	
	private JDBCManager manager;

	public JDBCPatientTreatmentManager(JDBCManager manager) {
		
		this.manager = manager;
		
	}
	
	@Override
    public List<PatientTreatment> getPatientsTreatmentsByPatientid(int patientId) {
    	
		List<PatientTreatment> patientsTreatments = new ArrayList<PatientTreatment>();
    	try {
    		Statement stmt = manager.getConnection().createStatement();
    		String sql = "SELECT * FROM PatientTreatment WHERE patient_id = " + patientId;
			ResultSet rs= stmt.executeQuery(sql);
			
			JDBCTreatmentManager jdbcTreatmentManager = new JDBCTreatmentManager(manager);
	        JDBCPatientManager jdbcPatientManager = new JDBCPatientManager(manager);
			
			while(rs.next())
			{
				
				//int id = rs.getInt("id");
	            int treatmentId = rs.getInt("treatment_id");
	            String comment = rs.getString("comment");
	            Date date = rs.getDate("date");
				
				
	            Treatment treatment = jdbcTreatmentManager.getTreatmentByid(treatmentId);
				
	            Patient patient = new Patient();
	            patient.setId(patientId);

	            PatientTreatment pt = new PatientTreatment(comment, date, treatment,patient);
	            patientsTreatments.add(pt);
				
				
			}
            
			
			rs.close();
			stmt.close();
			
			
    	}catch(Exception e) 
		{
			e.printStackTrace();
		}
    	return patientsTreatments;
    }
	

}
