package dentalClinicJDBC;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.AppointmentManager;
import dentalClinicJPA.JPAUserManager;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.Role;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Treatment;

public class JDBCAppointmentManager implements AppointmentManager {
	
	private JDBCManager manager;
	
	public JDBCAppointmentManager(JDBCManager manager) {
		
		this.manager = manager;
		
	}
	
	public void addAppointment(Appointment appointment) {
		String sql = "INSERT INTO Appointments (date, comment, patient_id, treatment_id, clinician_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            java.sql.Date sqlDate = new java.sql.Date(appointment.getDate().getTime());
            ps.setDate(1, sqlDate);
            ps.setString(2, appointment.getComment());
            ps.setInt(3, appointment.getPatient().getPatient_id());
            ps.setInt(4, appointment.getTreatment().getTreatment_id());
            ps.setInt(5, appointment.getClinician().getClinician_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
     }
     
	
	public void deleteAppointment (Integer appointment_id) {
		 String sql = "DELETE FROM Appointments WHERE appointment_id = ?";
	        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	            ps.setInt(1, appointment_id);
	            ps.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	public void updateAppointment(Integer appointment_id, Date newDate, Integer patient_id, Integer treatment_id, Integer clinician_id) {
		String sql = "UPDATE Appointments SET date = ?, comment = ?, patient_id = ?, treatment_id = ?, clinician_id = ? WHERE appointment_id = ?";
		
	    try {
	        PreparedStatement ps = manager.getConnection().prepareStatement(sql);
	        
	        ps.setDate(1, newDate);
	        ps.setString(2, "Updated comment");
	        ps.setInt(3, patient_id);
	        ps.setInt(4, treatment_id);
	        ps.setInt(5, clinician_id);
	        ps.setInt(6, appointment_id);
	        
	        

	        ps.executeUpdate();
	        ps.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		 
	}
	
	@Override
	public List<Appointment> getListOfAppointments(String email, Role role) {
	    List<Appointment> appointments = new ArrayList<>();
	    
	    try {
	        JDBCPatientManager patientManager = new JDBCPatientManager(manager);
	        JDBCClinicianManager clinicianManager = new JDBCClinicianManager(manager);
	        
	        if (role.getDescription().equalsIgnoreCase("Patient")) {
	            Patient patient = patientManager.getPatientByEmail(email);
	            if (patient != null) {
	                appointments = getAppointmentOfPatient(patient.getPatient_id());
	            }
	        } else if (role.getDescription().equalsIgnoreCase("Clinician")) {
	            Clinician clinician = clinicianManager.getClinicianByEmail(email);
	            if (clinician != null) {
	                appointments = getAppointmentOfClinician(clinician.getClinician_id());
	            }
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return appointments;
	}
	
	public List <Appointment> getAppointmentOfPatient (Integer patient_id){
		List<Appointment> appointments = new ArrayList<Appointment>();
		JDBCTreatmentManager jdbcTreatmentManager = new JDBCTreatmentManager(manager);
        JDBCPatientManager jdbcPatientManager = new JDBCPatientManager(manager);
        JDBCClinicianManager jdbcClinicianManager = new JDBCClinicianManager(manager);
        
		try {
			
			Statement stmt = manager.getConnection().createStatement();
    		String sql = "SELECT * FROM Appointments WHERE patient_id = " + patient_id;
			ResultSet rs= stmt.executeQuery(sql);
			
			while(rs.next()) {
				Date date = rs.getDate("date");
	            String comment = rs.getString("comment");
	            Integer treatment_id = rs.getInt("treatment_id");
	            Integer clinician_id = rs.getInt("clinician_id");
	            
	            Treatment treatment = jdbcTreatmentManager.getTreatmentById(treatment_id);
	            Patient patient = jdbcPatientManager.getPatientById(patient_id);
	            Clinician clinician = jdbcClinicianManager.getClinicianById(clinician_id);
	            Appointment pt = new Appointment(date, comment,patient, treatment, clinician);
	            appointments.add(pt);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		return appointments;
	}
	
	public List <Appointment> getAppointmentOfTreatments (Integer treatment_id){
		
		List<Appointment> appointments = new ArrayList<Appointment>();
		JDBCTreatmentManager jdbcTreatmentManager = new JDBCTreatmentManager(manager);
        JDBCPatientManager jdbcPatientManager = new JDBCPatientManager(manager);
        JDBCClinicianManager jdbcClinicianManager = new JDBCClinicianManager(manager);
        
		try {
			
			Statement stmt = manager.getConnection().createStatement();
    		String sql = "SELECT * FROM Appointments WHERE treatment_id = " + treatment_id;
			ResultSet rs= stmt.executeQuery(sql);
			
			while(rs.next()) {
				Date date = rs.getDate("date");
	            String comment = rs.getString("comment");
	            Integer patient_id = rs.getInt("patient_id");
	            Integer clinician_id = rs.getInt("clinician_id");
	            
	            Treatment treatment = jdbcTreatmentManager.getTreatmentById(treatment_id);
	            Patient patient = jdbcPatientManager.getPatientById(patient_id);
	            Clinician clinician = jdbcClinicianManager.getClinicianById(clinician_id);
	            Appointment pt = new Appointment(date, comment,patient, treatment, clinician);
	            appointments.add(pt);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		return appointments;
	}
	
	public List <Appointment> getAppointmentOfClinician (Integer clinician_id){
		
		List<Appointment> appointments = new ArrayList<Appointment>();
		JDBCTreatmentManager jdbcTreatmentManager = new JDBCTreatmentManager(manager);
        JDBCPatientManager jdbcPatientManager = new JDBCPatientManager(manager);
        JDBCClinicianManager jdbcClinicianManager = new JDBCClinicianManager(manager);
        
		try {
			
			Statement stmt = manager.getConnection().createStatement();
    		String sql = "SELECT * FROM Appointments WHERE clinician_id = " + clinician_id;
			ResultSet rs= stmt.executeQuery(sql);
			
			while(rs.next()) {
				Date date = rs.getDate("date");
	            String comment = rs.getString("comment");
	            Integer patient_id = rs.getInt("patient_id");
	            Integer treatment_id = rs.getInt("treatment_id");
	            
	            Treatment treatment = jdbcTreatmentManager.getTreatmentById(treatment_id);
	            Patient patient = jdbcPatientManager.getPatientById(patient_id);
	            Clinician clinician = jdbcClinicianManager.getClinicianById(clinician_id);
	            Appointment pt = new Appointment(date, comment,patient, treatment, clinician);
	            appointments.add(pt);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		return appointments;
	}

	
	/*
	public List <Appointment> getListOfAppointments(){
		 List<Appointment> appointments = new ArrayList<>();
	        try {
	            Statement stmt = manager.getConnection().createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT * FROM Appointments");
	            JDBCTreatmentManager jdbcTreatmentManager = new JDBCTreatmentManager(manager);
	            JDBCPatientManager jdbcPatientManager = new JDBCPatientManager(manager);
	            JDBCClinicianManager jdbcClinicianManager = new JDBCClinicianManager(manager);

	            while (rs.next()) {
	                Integer appointment_id = rs.getInt("appointment_id");
	                Date date = rs.getDate("date");
	                String comment = rs.getString("comment");
	                Integer patient_id = rs.getInt("patient_id");
	                Integer treatment_id = rs.getInt("treatment_id");
	                Integer clinician_id = rs.getInt("clinician_id");

	                Patient patient = jdbcPatientManager.getPatientById(patient_id);
	                Treatment treatment = jdbcTreatmentManager.getTreatmentById(treatment_id);
	                Clinician clinician = jdbcClinicianManager.getClinicianById(clinician_id);

	                Appointment appt = new Appointment(date, comment, patient, treatment, clinician);
	                appt.setAppointment_id(appointment_id);
	                appointments.add(appt);
	            }
	            rs.close();
	            stmt.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return appointments;
	}
	*/
	
	//TODO: REVISAR
	/*
	@Override
    public List<Appointment> getPatientsTreatmentsByPatientid(int patientId) {
    	
		List<Appointment> patientsTreatments = new ArrayList<Appointment>();
    	try {
    		Statement stmt = manager.getConnection().createStatement();
    		String sql = "SELECT * FROM Patient_treatments WHERE patient_id = " + patientId;
			ResultSet rs= stmt.executeQuery(sql);
			
			JDBCTreatmentManager jdbcTreatmentManager = new JDBCTreatmentManager(manager);
	        JDBCPatientManager jdbcPatientManager = new JDBCPatientManager(manager);
			
			while(rs.next())
			{
				
				//int id = rs.getInt("id");
	            Integer appointment_id = rs.getInt("appointment_id");
	            String comment = rs.getString("comment");
	            Date date = rs.getDate("date");
	            Integer treatmentId = rs.getInt("appointment_id");
				
				
	            Treatment treatment = jdbcTreatmentManager.getTreatmentByid(treatmentId);
	            Patient patient = jdbcPatientManager.getPatientByid(patientId)
	            Appointment pt = new Appointment(appointment_id, date, comment,patient, treatment,clinician);
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
	*/

	/*
	@Override
	public void addAppointment(Appointment newAppointment) {
	    try {
	        String sql = "INSERT INTO Patient_treatments (appointment_id,patient_id,treatment_ic,clinician_id, date, comments) VALUES (?, ?, ?, ?, ?, ?)";
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
	*/

	/*
	@Override
	public void updatePatientTreatment(Appointment appointmentToModify) {
	    try {
	        String sql = "UPDATE Patient_treatments SET comment = ?, date = ? WHERE id = ?";
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
	*/

	/*
	@Override
	public Appointment getPatientTreatmentById(int appointmentId) {
	    Appointment pt = null;
	    try {
	        String sql = "SELECT * FROM Patient_treatments WHERE id = ?";
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

	            pt = new Appointment(comment, date, treatment, patient);
	            pt.setId(appointmentId);
	        }

	        rs.close();
	        pstmt.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return pt;
	}
	*/

	/*
	@Override
	public void removePatientTreatment(Appointment appointmentToCancel) {
	    try {
	        String sql = "DELETE FROM Patient_treatments WHERE id = ?";
	        PreparedStatement pstmt = manager.getConnection().prepareStatement(sql);
	        pstmt.setInt(1, appointmentToCancel.getId());
	        pstmt.executeUpdate();
	        pstmt.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	*/

	/*
	@Override
	public List<Appointment> getAllPatientTreatments() {
	    List<Appointment> appointments = new ArrayList<>();
	    try {
	        Statement stmt = manager.getConnection().createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT * FROM Patient_treatments");

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

	            Appointment pt = new Appointment(comment, date, treatment, patient);
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
	*/
	
	//TODO: crear, borrar, modificar, verTodos, getByid
	
				
	

	
}