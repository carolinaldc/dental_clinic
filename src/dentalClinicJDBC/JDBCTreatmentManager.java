package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.TreatmentManager;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Treatment;

public class JDBCTreatmentManager implements TreatmentManager {

    private JDBCManager manager;

    public JDBCTreatmentManager(JDBCManager connectionManager) {
        this.manager = connectionManager;
    }
    
    
    @Override
    public void addTreatment(Treatment treatment) {
    	String sql = "INSERT INTO Treatments (name, description, price) VALUES (?, ?, ?)";
        ResultSet generatedKeys = null;

        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, treatment.getName());
            ps.setString(2, treatment.getDescription());
            ps.setInt(3, treatment.getPrice());
            ps.executeUpdate();

            generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int treatmentId = generatedKeys.getInt(1);
                treatment.setTreatment_id(treatmentId);  // Save for future use

                // Now link materials
                JDBCTreatmentMaterialsManager treatmentMaterialsManager = new JDBCTreatmentMaterialsManager(manager);
                for (Material material : treatment.getMaterials()) {
                    treatmentMaterialsManager.linkMaterialToTreatment(material.getMaterials_id(), treatmentId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        
    }

    
	public void deleteTreatment (Integer treatment_id) {
		String sql = "DELETE FROM Treatments WHERE treatment_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, treatment_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	@Override
	public void updateTreatment(Treatment treatment) {
		
		String updatesql = "UPDATE Treatments SET name = ?, description = ?, price = ? WHERE treatment_id = ?";
		String deleteMaterialsSQL = "DELETE FROM Treatment_Materials WHERE treatment_id = ?";
	    String insertMaterialSQL = "INSERT INTO Treatment_Materials (treatment_id, materials_id) VALUES (?, ?)";
		
	    try {
	        PreparedStatement ps = manager.getConnection().prepareStatement(updatesql);

	        ps.setString(1, treatment.getName());
	        ps.setString(2, treatment.getDescription());
	        ps.setInt(3, treatment.getPrice());
	        ps.setInt(4, treatment.getTreatment_id());

	        ps.executeUpdate();
	        ps.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    try {
	    	PreparedStatement ps = manager.getConnection().prepareStatement(deleteMaterialsSQL);
	    	ps.setInt(1, treatment.getTreatment_id());
            ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } 
	    
	    try {
	    	PreparedStatement ps = manager.getConnection().prepareStatement(insertMaterialSQL);
	    	
	    	for (Material material : treatment.getMaterials()) {
                ps.setInt(1, treatment.getTreatment_id());
                ps.setInt(2, material.getMaterials_id());
                ps.addBatch(); // Batch insertion
            }
            ps.executeBatch();
	    	
	    	
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } 
	    
	    
	}
	
	public List <Treatment> getListOfTreatments(){
		 List<Treatment> treatments = new ArrayList<>();
	        JDBCAppointmentManager jdbcAppointmentManager = new JDBCAppointmentManager(manager);
	        JDBCMaterialManager jdbcMaterialManager = new JDBCMaterialManager(manager);

	        String sql = "SELECT * FROM Treatments";
	        try (Statement stmt = manager.getConnection().createStatement();
	             ResultSet rs = stmt.executeQuery(sql)) {

	            while (rs.next()) {
	                Integer id = rs.getInt("treatment_id");
	                String name = rs.getString("name");
	                String description = rs.getString("description");
	                Integer price = rs.getInt("price");

	                List<Appointment> appointments = jdbcAppointmentManager.getAppointmentOfTreatments(id);
	                List<Material> materials = jdbcMaterialManager.getListOfTreatment_Materials(id);

	                Treatment treatment = new Treatment(name, description, price, appointments, materials);
	                treatment.setTreatment_id(id);
	                treatments.add(treatment);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return treatments;
	}
	
	
	public Treatment getTreatmentById(Integer treatment_id) {
	    Treatment treatment = null;
	    JDBCMaterialManager jdbcMaterialManager = new JDBCMaterialManager(manager);

	    try {
	        Statement stmt = manager.getConnection().createStatement();
	        String sql = "SELECT * FROM Treatments WHERE treatment_id = " + treatment_id;
	        ResultSet rs = stmt.executeQuery(sql);

	        if (rs.next()) {
	            String name = rs.getString("name");
	            String description = rs.getString("description");
	            Integer price = rs.getInt("price");

	            List<Appointment> appointments = new ArrayList<>();
	            List<Material> materials = jdbcMaterialManager.getListOfTreatment_Materials(treatment_id);

	            treatment = new Treatment(name, description, price, appointments, materials);
	            treatment.setTreatment_id(treatment_id);
	        }

	        rs.close();
	        stmt.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return treatment;
	}

	/*
	public List<Treatment> getTreatmentsOfMaterial(Integer material_id) {
	    List<Treatment> treatments = new ArrayList<>();

	    try {
	        String sql = "SELECT treatment_id FROM Treatment_materials WHERE materials_id = ?";
	        PreparedStatement ps = manager.getConnection().prepareStatement(sql);
	        ps.setInt(1, material_id);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            int treatmentId = rs.getInt("treatment_id");
	            Treatment treatment = getTreatmentById(treatmentId); // este método ya lo tienes
	            treatments.add(treatment);
	        }

	        rs.close();
	        ps.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return treatments;
	}
	*/

    
    /*
    public void addTreatment(Treatment treatment) {
        try {
            String sql = "INSERT INTO Treatments (name, description, price, room_id) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conMan.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, treatment.getName());
            ps.setString(2, treatment.getDescription());
            ps.setInt(3, treatment.getPrice());
            ps.setInt(4, treatment.getRoom().getRoom_id());  
            ps.executeUpdate();

           
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                treatment.setTreatment_id(rs.getInt(1)); 
            }

     
            for (Clinician clinician : treatment.getClinician()) {
                String clinicianSql = "INSERT INTO TreatmentClinicians (treatment_id, clinician_id) VALUES (?, ?)";
                PreparedStatement psClinician = conMan.getConnection().prepareStatement(clinicianSql);
                psClinician.setInt(1, treatment.getTreatment_id());
                psClinician.setInt(2, clinician.getClinicianId()); 
                psClinician.executeUpdate();
            }

           
            for (Appointment appointment : treatment.getAppointment()) {
                String appointmentSql = "INSERT INTO PatientTreatments (treatment_id, patient_id, appointment_date) VALUES (?, ?, ?)";
                PreparedStatement psAppointment = conMan.getConnection().prepareStatement(appointmentSql);
                psAppointment.setInt(1, treatment.getTreatment_id());
                psAppointment.setInt(2, appointment.getPatient().getId()); 
                psAppointment.setDate(3, Date.valueOf(appointment.getAppointmentDate())); 
                psAppointment.executeUpdate();
            }

            ps.close();
        } catch (SQLException e) {
            System.out.println("Error inserting treatment");
            e.printStackTrace();
        }
    }


    public List<Treatment> getAllTreatments() {
        List<Treatment> treatments = new ArrayList<>();
        try {
            String sql = "SELECT treatment_id, name, description, price, room_id FROM Treatments";
            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int treatmentId = rs.getInt("treatment_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                int roomId = rs.getInt("room_id");

                
                Room room = getRoomById(roomId);

                Treatment treatment = new Treatment();
                treatment.setTreatment_id(treatmentId);
                treatment.setName(name);
                treatment.setDescription(description);
                treatment.setPrice(price);
                treatment.setRoom(room);

                
                List<Clinician> clinicians = getCliniciansByTreatmentId(treatmentId);
                treatment.setClinician(clinicians);

               
                List<Appointment> appointments = getAppointmentsByTreatmentId(treatmentId);
                treatment.setAppointment(appointments);

                treatments.add(treatment);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving treatments");
            e.printStackTrace();
        }
        return treatments;
    }

   
    public void updateTreatment(Treatment treatment) {
        try {
            String sql = "UPDATE Treatments SET name = ?, description = ?, price = ?, room_id = ? WHERE treatment_id = ?";
            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
            ps.setString(1, treatment.getName());
            ps.setString(2, treatment.getDescription());
            ps.setInt(3, treatment.getPrice());
            ps.setInt(4, treatment.getRoom().getId_room());
            ps.setInt(5, treatment.getTreatment_id());
            ps.executeUpdate();


            clearTreatmentClinicians(treatment.getTreatment_id());
            for (Clinician clinician : treatment.getClinician()) {
                String clinicianSql = "INSERT INTO TreatmentClinicians (treatment_id, clinician_id) VALUES (?, ?)";
                PreparedStatement psClinician = conMan.getConnection().prepareStatement(clinicianSql);
                psClinician.setInt(1, treatment.getTreatment_id());
                psClinician.setInt(2, clinician.getClinicianId());
                psClinician.executeUpdate();
            }

         
            clearTreatmentAppointments(treatment.getTreatment_id());
            for (Appointment appointment : treatment.getAppointment()) {
                String appointmentSql = "INSERT INTO PatientTreatments (treatment_id, patient_id, appointment_date) VALUES (?, ?, ?)";
                PreparedStatement psAppointment = conMan.getConnection().prepareStatement(appointmentSql);
                psAppointment.setInt(1, treatment.getTreatment_id());
                psAppointment.setInt(2, appointment.getPatient().getId());
                psAppointment.setDate(3, Date.valueOf(appointment.getAppointmentDate()));
                psAppointment.executeUpdate();
            }

            ps.close();
        } catch (SQLException e) {
            System.out.println("Error updating treatment");
            e.printStackTrace();
        }
    }


    public void deleteTreatment(int treatmentId) {
        try {
            String sql = "DELETE FROM Treatments WHERE treatment_id = ?";
            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
            ps.setInt(1, treatmentId);
            ps.executeUpdate();

            
            clearTreatmentClinicians(treatmentId);
            clearTreatmentAppointments(treatmentId);

            ps.close();
        } catch (SQLException e) {
            System.out.println("Error deleting treatment");
            e.printStackTrace();
        }
    }

    private Room getRoomById(int roomId) {
        
        return new Room(); 
    }
    
    @Override
    public Treatment getTreatmentByid(int id) {
    	
		Treatment treatment = null;
		JDBCRoomManager jdbcRoomManager = new JDBCRoomManager(conMan);
		JDBCClinicianManager jdbcClinicianManager = new JDBCClinicianManager(conMan);
        JDBCMaterialManager jdbcMaterialManager = new JDBCMaterialManager(conMan);
        
    	try {
    		Statement stmt = conMan.getConnection().createStatement();
    		String sql = "SELECT * FROM Treatment WHERE id=" + id;
			
			ResultSet rs= stmt.executeQuery(sql);
			
			String name = rs.getString("name");
			String description = rs.getString("description");
			Integer price = rs.getInt("price");
			Integer room_id = rs.getInt("room_id");
			
			//Room room = jdbcRoomManager.getRoomById(room_id);
			//List<Clinician> clinicians = jdbcClinicianManager.getCliniciansByTreatmentId(id);
            //List<Material> materials = jdbcMaterialManager.getAllMaterials();
			
		
			
            //TODO Hay que meter tambien appointment, room y material?
			
			rs.close();
			stmt.close();
			
			treatment = new Treatment(name, description, price);
			
    	}catch(Exception e) 
		{
			e.printStackTrace();
		}
    	return treatment;
    }

    private List<Clinician> getCliniciansByTreatmentId(int treatmentId) {
        List<Clinician> clinicians = new ArrayList<>();
        try {
            String sql = "SELECT clinician_id FROM TreatmentClinicians WHERE treatment_id = ?";
            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
            ps.setInt(1, treatmentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int clinicianId = rs.getInt("clinician_id");
                Clinician clinician = getClinicianById(clinicianId);
                clinicians.add(clinician);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving clinicians for treatment");
            e.printStackTrace();
        }
        return clinicians;
    }

    private Clinician getClinicianById(int clinicianId) {
      
        return new Clinician(); 
    }

    private List<Appointment> getAppointmentsByTreatmentId(int treatmentId) {
        List<Appointment> appointments = new ArrayList<>();
        try {
            String sql = "SELECT patient_id, appointment_date FROM PatientTreatments WHERE treatment_id = ?";
            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
            ps.setInt(1, treatmentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int patientId = rs.getInt("patient_id");
                Date appointmentDate = rs.getDate("appointment_date");
                //PatientTreatment appointment = new PatientTreatment(patientId, appointmentDate.toLocalDate());
                //appointments.add(appointment);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving appointments for treatment");
            e.printStackTrace();
        }
        return appointments;
    }

    private void clearTreatmentClinicians(int treatmentId) {
        try {
            String sql = "DELETE FROM TreatmentClinicians WHERE treatment_id = ?";
            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
            ps.setInt(1, treatmentId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error clearing clinicians for treatment");
            e.printStackTrace();
        }
    }

    private void clearTreatmentAppointments(int treatmentId) {
        try {
            String sql = "DELETE FROM PatientTreatments WHERE treatment_id = ?";
            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
            ps.setInt(1, treatmentId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error clearing appointments for treatment");
            e.printStackTrace();
        }
    }
    */


}