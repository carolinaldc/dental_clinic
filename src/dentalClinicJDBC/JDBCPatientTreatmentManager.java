package dentalClinicJDBC;

import java.sql.Date;
import java.sql.PreparedStatement;
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
	

	@Override
	public void addPatientTreatment(PatientTreatment newAppointment) {
	    try {
	        String sql = "INSERT INTO PatientTreatment (comment, date, treatment_id, patient_id) VALUES (?, ?, ?, ?)";
	        PreparedStatement pstmt = manager.getConnection().prepareStatement(sql);
	        pstmt.setString(1, newAppointment.getComment());
	        pstmt.setDate(2, new java.sql.Date(newAppointment.getDate().getTime()));
	        pstmt.setInt(3, newAppointment.getTreatment().getTreatment_id());
	        pstmt.setInt(4, newAppointment.getPatient().getId());
	        pstmt.executeUpdate();
	        pstmt.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void updatePatientTreatment(PatientTreatment appointmentToModify) {
	    try {
	        String sql = "UPDATE PatientTreatment SET comment = ?, date = ? WHERE id = ?";
	        PreparedStatement pstmt = manager.getConnection().prepareStatement(sql);
	        pstmt.setString(1, appointmentToModify.getComment());
	        pstmt.setDate(2, new java.sql.Date(appointmentToModify.getDate().getTime()));
	        pstmt.setInt(3, appointmentToModify.getId());
	        pstmt.executeUpdate();
	        pstmt.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public PatientTreatment getPatientTreatmentById(int appointmentId) {
	    PatientTreatment pt = null;
	    try {
	        String sql = "SELECT * FROM PatientTreatment WHERE id = ?";
	        PreparedStatement pstmt = manager.getConnection().prepareStatement(sql);
	        pstmt.setInt(1, appointmentId);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            int treatmentId = rs.getInt("treatment_id");
	            int patientId = rs.getInt("patient_id");
	            String comment = rs.getString("comment");
	            Date date = rs.getDate("date");

	            Treatment treatment = new JDBCTreatmentManager(manager).getTreatmentByid(treatmentId);
	            Patient patient = new Patient();
	            patient.setId(patientId);

	            pt = new PatientTreatment(comment, date, treatment, patient);
	            pt.setId(appointmentId);
	        }

	        rs.close();
	        pstmt.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return pt;
	}

	@Override
	public void removePatientTreatment(PatientTreatment appointmentToCancel) {
	    try {
	        String sql = "DELETE FROM PatientTreatment WHERE id = ?";
	        PreparedStatement pstmt = manager.getConnection().prepareStatement(sql);
	        pstmt.setInt(1, appointmentToCancel.getId());
	        pstmt.executeUpdate();
	        pstmt.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public List<PatientTreatment> getAllPatientTreatments() {
	    List<PatientTreatment> appointments = new ArrayList<>();
	    try {
	        Statement stmt = manager.getConnection().createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT * FROM PatientTreatment");

	        JDBCTreatmentManager treatmentManager = new JDBCTreatmentManager(manager);

	        while (rs.next()) {
	            int id = rs.getInt("id");
	            int treatmentId = rs.getInt("treatment_id");
	            int patientId = rs.getInt("patient_id");
	            String comment = rs.getString("comment");
	            Date date = rs.getDate("date");

	            Treatment treatment = treatmentManager.getTreatmentByid(treatmentId);
	            Patient patient = new Patient();
	            patient.setId(patientId);

	            PatientTreatment pt = new PatientTreatment(comment, date, treatment, patient);
	            pt.setId(id);
	            appointments.add(pt);
	        }

	        rs.close();
	        stmt.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return appointments;
	}
}
