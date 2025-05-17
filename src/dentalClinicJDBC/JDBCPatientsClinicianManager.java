package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.PatientsClinicianManager;
import dentalClinicPOJOS.Patients_Clinician;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.Clinician;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class JDBCPatientsClinicianManager implements PatientsClinicianManager {

	private JDBCManager manager;

	public JDBCPatientsClinicianManager(JDBCManager manager) {
		
		this.manager = manager;
	}
	
	@Override
	public void addPatientClinician(Patients_Clinician pc) {
        try {
            String sql = "INSERT INTO Patients_clinicians (patient_id, clinician_id, visit_info, date) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            ps.setInt(1, pc.getPatient().getId());
            ps.setInt(2, pc.getClinician().getClinicianId());
            ps.setString(3, pc.getVisitInfo());
            ps.setDate(4, pc.getDate());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error inserting patient-clinician relation");
            e.printStackTrace();
        }
    }
	
	
	@Override
	public List<Patients_Clinician> getAllPatientsClinicians() {
        List<Patients_Clinician> relations = new ArrayList<>();
        try {
            String sql = "SELECT id, patient_id, clinician_id, visit_info, date FROM Patients_clinicians";
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
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
                clinician.setClinicianId(clinicianId);

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
	
	@Override
	public Patients_Clinician getPatientClinicianById(int id) {
        Patients_Clinician pc = null;
        try {
            String sql = "SELECT id, patient_id, clinician_id, visit_info, date FROM Patients_clinicians WHERE id = ?";
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
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
                clinician.setClinicianId(clinicianId);

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
	
	@Override
	public void updatePatientClinician(Patients_Clinician pc) {
        try {
            String sql = "UPDATE Patients_clinicians SET patient_id = ?, clinician_id = ?, visit_info = ?, date = ? WHERE id = ?";
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            ps.setInt(1, pc.getPatient().getId());
            ps.setInt(2, pc.getClinician().getClinicianId());
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
	
	
	@Override
	public void deletePatientClinician(int id) {
        try {
            String sql = "DELETE FROM Patients_clinicians WHERE id = ?";
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error deleting patient-clinician relation");
            e.printStackTrace();
        }
    }
	
	
	@Override
	public List<Patients_Clinician> getPatientsCliniciansByPatientid(int patientId) {
	    List<Patients_Clinician> result = new ArrayList<>();

	    try {
	        Statement stmt = manager.getConnection().createStatement();
	        String sql = "SELECT * FROM Patients_clinicians WHERE patient_id = " + patientId;
	        ResultSet rs = stmt.executeQuery(sql);

	        JDBCClinicianManager jdbcClinicianManager = new JDBCClinicianManager(manager);
	        JDBCPatientManager jdbcPatientManager = new JDBCPatientManager(manager); 

	        while (rs.next()) {
	            int id = rs.getInt("id");
	            int clinicianId = rs.getInt("clinician_id");
	            String visitInfo = rs.getString("visitInfo");
	            Date date = rs.getDate("date");

	            
	            Clinician clinician = jdbcClinicianManager.getClinicianByid(clinicianId);

	            
	            Patient patient = new Patient();
	            patient.setId(patientId); 

	            Patients_Clinician pc = new Patients_Clinician(visitInfo, date, clinician ,patient);
	            result.add(pc);
	        }

	        rs.close();
	        stmt.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}
	
	/*
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
    */
	
	
    

}

