package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalClinicPOJOS.Patients_Clinician;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.Clinician;

public class JDBCPatientsClinicianManager {

	  private Connection c;
	    private JDBCManager conMan;

	    public JDBCPatientsClinicianManager(JDBCManager connectionManager) {
	        this.conMan = connectionManager;
	        this.c = connectionManager.getConnection();
	    }
	    
	    
	    public void addPatientClinician(Patients_Clinician pc) {
	        try {
	            String sql = "INSERT INTO Patients_Clinician (patient_id, clinician_id, visit_info, date) VALUES (?, ?, ?, ?)";
	            PreparedStatement ps = c.prepareStatement(sql);
	            ps.setInt(1, pc.getPatient().getId());
	            ps.setInt(2, pc.getClinician().getId());
	            ps.setString(3, pc.getVisitInfo());
	            ps.setDate(4, pc.getDate());
	            ps.executeUpdate();
	            ps.close();
	        } catch (SQLException e) {
	            System.out.println("Error inserting patient-clinician relation");
	            e.printStackTrace();
	        }
	    }

	 
	    public List<Patients_Clinician> getAllPatientsClinicians() {
	        List<Patients_Clinician> relations = new ArrayList<>();
	        try {
	            String sql = "SELECT id, patient_id, clinician_id, visit_info, date FROM Patients_Clinician";
	            PreparedStatement ps = c.prepareStatement(sql);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                int id = rs.getInt("id");
	                int patientId = rs.getInt("patient_id");
	                int clinicianId = rs.getInt("clinician_id");
	                String visitInfo = rs.getString("visit_info");
	                Date date = rs.getDate("date");

	                Patient patient = new Patient();
	                patient.setId(patientId);  
	                Clinician clinician = new Clinician();
	                clinician.setId(clinicianId);

	                Patients_Clinician pc = new Patients_Clinician(visitInfo, date, clinician, patient);
	                pc.setId(id);
	                relations.add(pc);
	            }

	            rs.close();
	            ps.close();
	        } catch (SQLException e) {
	            System.out.println("Error retrieving patient-clinician relations");
	            e.printStackTrace();
	        }
	        return relations;
	    }


	    public Patients_Clinician getPatientClinicianById(int id) {
	        Patients_Clinician pc = null;
	        try {
	            String sql = "SELECT id, patient_id, clinician_id, visit_info, date FROM Patients_Clinician WHERE id = ?";
	            PreparedStatement ps = c.prepareStatement(sql);
	            ps.setInt(1, id);
	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                int patientId = rs.getInt("patient_id");
	                int clinicianId = rs.getInt("clinician_id");
	                String visitInfo = rs.getString("visit_info");
	                Date date = rs.getDate("date");

	                Patient patient = new Patient();
	                patient.setId(patientId);
	                Clinician clinician = new Clinician();
	                clinician.setId(clinicianId);

	                pc = new Patients_Clinician(visitInfo, date, clinician, patient);
	                pc.setId(id);
	            }

	            rs.close();
	            ps.close();
	        } catch (SQLException e) {
	            System.out.println("Error retrieving patient-clinician relation by ID");
	            e.printStackTrace();
	        }
	        return pc;
	    }

	
	    public void updatePatientClinician(Patients_Clinician pc) {
	        try {
	            String sql = "UPDATE Patients_Clinician SET patient_id = ?, clinician_id = ?, visit_info = ?, date = ? WHERE id = ?";
	            PreparedStatement ps = c.prepareStatement(sql);
	            ps.setInt(1, pc.getPatient().getId());
	            ps.setInt(2, pc.getClinician().getId());
	            ps.setString(3, pc.getVisitInfo());
	            ps.setDate(4, pc.getDate());
	            ps.setInt(5, pc.getId());
	            ps.executeUpdate();
	            ps.close();
	        } catch (SQLException e) {
	            System.out.println("Error updating patient-clinician relation");
	            e.printStackTrace();
	        }
	    }

	    public void deletePatientClinician(int id) {
	        try {
	            String sql = "DELETE FROM Patients_Clinician WHERE id = ?";
	            PreparedStatement ps = c.prepareStatement(sql);
	            ps.setInt(1, id);
	            ps.executeUpdate();
	            ps.close();
	        } catch (SQLException e) {
	            System.out.println("Error deleting patient-clinician relation");
	            e.printStackTrace();
	        }
	    }

	    public JDBCManager getConMan() {
	        return conMan;
	    }

	    public void setConMan(JDBCManager conMan) {
	        this.conMan = conMan;
	    }
	}
}

