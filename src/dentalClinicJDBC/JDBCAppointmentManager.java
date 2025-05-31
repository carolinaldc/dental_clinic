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
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Treatment;

public class JDBCAppointmentManager implements AppointmentManager {
	
	private JDBCManager manager;
	
	public JDBCAppointmentManager(JDBCManager manager) {
		
		this.manager = manager;
		
	}
    
	@Override
	public void addAppointment(Appointment appointment) {
	    String sql = "INSERT INTO Appointments (date, comments, patient_id, treatment_id, clinician_id) VALUES (?, ?, ?, ?, ?)";

	    try {
	        PreparedStatement ps = manager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        ps.setDate(1, new java.sql.Date(appointment.getDate().getTime()));
	        ps.setString(2, appointment.getComment());
	        ps.setInt(3, appointment.getPatient().getPatient_id());   
	        ps.setInt(4, appointment.getTreatment().getTreatment_id());
	        ps.setInt(5, appointment.getClinician().getClinician_id());

	        ps.executeUpdate();

	        ResultSet generatedKeys = ps.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            int generatedId = generatedKeys.getInt(1);
	            appointment.setAppointment_id(generatedId);
	        }

	        ps.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	@Override
	public void deleteAppointment (Integer appointment_id) {
		 String sql = "DELETE FROM Appointments WHERE appointment_id = ?";
	        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	            ps.setInt(1, appointment_id);
	            ps.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	/*
	public void updateAppointment(Integer appointment_id, Date newDate, Integer patient_id, Integer treatment_id, Integer clinician_id) {
		String sql = "UPDATE Appointments SET date = ?, comments = ?, patient_id = ?, treatment_id = ?, clinician_id = ? WHERE appointment_id = ?";
		
	    try {
	        PreparedStatement ps = manager.getConnection().prepareStatement(sql);
	        
	        ps.setDate(1, newDate);
	        ps.setString(2, "Updated comments");
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
	*/
	
	@Override
	public void updateAppointment(Appointment appt) {
	    String sql = "UPDATE Appointments SET date = ?, comments = ?, patient_id = ?, treatment_id = ?, clinician_id = ? WHERE appointment_id = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setDate(1, appt.getDate());
	        ps.setString(2, appt.getComment());
	        ps.setInt(3, appt.getPatient().getPatient_id());
	        ps.setInt(4, appt.getTreatment().getTreatment_id());
	        ps.setInt(5, appt.getClinician().getClinician_id());
	        ps.setInt(6, appt.getAppointment_id());

	        ps.executeUpdate();
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
	
	@Override
	public List <Appointment> getAppointmentOfPatient (Integer patient_id){
		List<Appointment> appointments = new ArrayList<Appointment>();
		JDBCTreatmentManager jdbcTreatmentManager = new JDBCTreatmentManager(manager);
        JDBCPatientManager jdbcPatientManager = new JDBCPatientManager(manager);
        JDBCClinicianManager jdbcClinicianManager = new JDBCClinicianManager(manager);
        
		try {
			
			Statement stmt = manager.getConnection().createStatement();
    		String sql = "SELECT * FROM Appointments WHERE patient_id = " + patient_id;
			ResultSet rs= stmt.executeQuery(sql);
			
			while (rs.next()) {
			    Integer appointment_id = rs.getInt("appointment_id");
			    Date date = rs.getDate("date");
			    String comment = rs.getString("comments");
			    Integer treatment_id = rs.getInt("treatment_id");
			    Integer clinician_id = rs.getInt("clinician_id");

			    Treatment treatment = jdbcTreatmentManager.getTreatmentById(treatment_id);
			    Patient patient = jdbcPatientManager.getPatientById(patient_id);
			    Clinician clinician = jdbcClinicianManager.getClinicianById(clinician_id);

			    Appointment pt = new Appointment(appointment_id, date, comment, patient, treatment, clinician);
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
	
	@Override
	public List<Appointment> getAppointmentOfTreatments(Integer treatment_id) {
	    List<Appointment> appointments = new ArrayList<>();
	    JDBCTreatmentManager jdbcTreatmentManager = new JDBCTreatmentManager(manager);
	    JDBCPatientManager jdbcPatientManager = new JDBCPatientManager(manager);
	    JDBCClinicianManager jdbcClinicianManager = new JDBCClinicianManager(manager);

	    try {
	        Statement stmt = manager.getConnection().createStatement();
	        String sql = "SELECT * FROM Appointments WHERE treatment_id = " + treatment_id;
	        ResultSet rs = stmt.executeQuery(sql);

	        while (rs.next()) {
			    Integer appointment_id = rs.getInt("appointment_id");
	            Date date = rs.getDate("date");
	            String comment = rs.getString("comments");
	            Integer patient_id = rs.getInt("patient_id");
	            Integer clinician_id = rs.getInt("clinician_id");

	            Treatment treatment = jdbcTreatmentManager.getTreatmentById(treatment_id);
	            Patient patient = jdbcPatientManager.getPatientById(patient_id);
	            Clinician clinician = jdbcClinicianManager.getClinicianById(clinician_id);

	            Appointment appointment = new Appointment(appointment_id, date, comment, patient, treatment, clinician);
	            appointments.add(appointment);
	        }

	        rs.close();
	        stmt.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

		return appointments;
	}

	
	@Override
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
			    Integer appointment_id = rs.getInt("appointment_id");
				Date date = rs.getDate("date");
	            String comment = rs.getString("comments");
	            Integer patient_id = rs.getInt("patient_id");
	            Integer treatment_id = rs.getInt("treatment_id");
	            
	            Treatment treatment = jdbcTreatmentManager.getTreatmentById(treatment_id);
	            Patient patient = jdbcPatientManager.getPatientById(patient_id);
	            Clinician clinician = jdbcClinicianManager.getClinicianById(clinician_id);
	            Appointment pt = new Appointment(appointment_id, date, comment,patient, treatment, clinician);
	            /*
	            Treatment treatment = new Treatment();
	            treatment.setTreatment_id(treatment_id); //hacer esto para que no entre en bucle infinito
	            //Treatment treatment = jdbcTreatmentManager.getTreatmentById(treatment_id);
	            Patient patient = new Patient();
	            patient.setPatient_id(patient_id);
	            //Patient patient = jdbcPatientManager.getPatientById(patient_id);
	            Clinician clinician = new Clinician();
	            clinician.setClinician_id(clinician_id);
	            //Clinician clinician = jdbcClinicianManager.getClinicianById(clinician_id);
	            Appointment pt = new Appointment(date, comment,patient, treatment, clinician);
	            */
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
	
	@Override
	public Appointment getAppointmentById(Integer appointment_id) {
	    Appointment appointment = null;
	    JDBCPatientManager jdbcPatientManager = new JDBCPatientManager(manager);
	    JDBCClinicianManager jdbcClinicianManager = new JDBCClinicianManager(manager);
	    JDBCTreatmentManager jdbcTreatmentManager = new JDBCTreatmentManager(manager);
	    try {
	        Statement stmt = manager.getConnection().createStatement();
	        String sql = "SELECT * FROM Appointments WHERE appointment_id = " + appointment_id;
	        ResultSet rs = stmt.executeQuery(sql);

	        if (rs.next()) {
	            Date date = rs.getDate("date");
	            String comments = rs.getString("comments");
	            Integer patient_id = rs.getInt("patient_id");
	            Patient patient = jdbcPatientManager.getPatientById(patient_id);
	            Integer clinician_id = rs.getInt("clinician_id");
	            Clinician clinician = jdbcClinicianManager.getClinicianById(clinician_id);
	            Integer treatment_id = rs.getInt("treatment_id");
	            Treatment treatment = jdbcTreatmentManager.getTreatmentById(clinician_id);
	            
	            
	            appointment = new Appointment(appointment_id, date, comments, patient, treatment, clinician);
	        }

	        rs.close();
	        stmt.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return appointment;
	}
	
	@Override
	public void updateAppointmentDate(int appointmentId, Date newDate) {
	    String sql = "UPDATE Appointments SET date = ? WHERE appointment_id = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setDate(1, newDate);
	        ps.setInt(2, appointmentId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void updateAppointmentComments(int appointmentId, String comments) {
	    String sql = "UPDATE Appointments SET comments = ? WHERE appointment_id = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setString(1, comments);
	        ps.setInt(2, appointmentId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	@Override
	public void updateAppointmentPatient(int appointmentId, int patientId) {
	    String sql = "UPDATE Appointments SET patient_id = ? WHERE appointment_id = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setInt(1, patientId);
	        ps.setInt(2, appointmentId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void updateAppointmentTreatment(int appointmentId, int treatmentId) {
	    String sql = "UPDATE Appointments SET treatment_id = ? WHERE appointment_id = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setInt(1, treatmentId);
	        ps.setInt(2, appointmentId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void updateAppointmentClinician(int appointmentId, int clinicianId) {
	    String sql = "UPDATE Appointments SET clinician_id = ? WHERE appointment_id = ?";
	    try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
	        ps.setInt(1, clinicianId);
	        ps.setInt(2, appointmentId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
}