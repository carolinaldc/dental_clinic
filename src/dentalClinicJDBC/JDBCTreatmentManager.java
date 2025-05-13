package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.TreatmentManager;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.PatientTreatment;
import dentalClinicPOJOS.Room;
import dentalClinicPOJOS.Treatment;

public class JDBCTreatmentManager implements TreatmentManager {

    private Connection c;
    private JDBCManager conMan;

    public JDBCTreatmentManager(JDBCManager connectionManager) {
        this.conMan = connectionManager;
        this.c = connectionManager.getConnection();
    }
    
    public void addTreatment(Treatment treatment) {
        try {
            String sql = "INSERT INTO Treatments (name, description, price, room_id) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
                PreparedStatement psClinician = c.prepareStatement(clinicianSql);
                psClinician.setInt(1, treatment.getTreatment_id());
                psClinician.setInt(2, clinician.getId()); 
                psClinician.executeUpdate();
            }

           
            for (PatientTreatment appointment : treatment.getAppointment()) {
                String appointmentSql = "INSERT INTO PatientTreatments (treatment_id, patient_id, appointment_date) VALUES (?, ?, ?)";
                PreparedStatement psAppointment = c.prepareStatement(appointmentSql);
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
            PreparedStatement ps = c.prepareStatement(sql);
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

               
                List<PatientTreatment> appointments = getAppointmentsByTreatmentId(treatmentId);
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
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, treatment.getName());
            ps.setString(2, treatment.getDescription());
            ps.setInt(3, treatment.getPrice());
            ps.setInt(4, treatment.getRoom().getId_room());
            ps.setInt(5, treatment.getTreatment_id());
            ps.executeUpdate();


            clearTreatmentClinicians(treatment.getTreatment_id());
            for (Clinician clinician : treatment.getClinician()) {
                String clinicianSql = "INSERT INTO TreatmentClinicians (treatment_id, clinician_id) VALUES (?, ?)";
                PreparedStatement psClinician = c.prepareStatement(clinicianSql);
                psClinician.setInt(1, treatment.getTreatment_id());
                psClinician.setInt(2, clinician.getId());
                psClinician.executeUpdate();
            }

         
            clearTreatmentAppointments(treatment.getTreatment_id());
            for (PatientTreatment appointment : treatment.getAppointment()) {
                String appointmentSql = "INSERT INTO PatientTreatments (treatment_id, patient_id, appointment_date) VALUES (?, ?, ?)";
                PreparedStatement psAppointment = c.prepareStatement(appointmentSql);
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
            PreparedStatement ps = c.prepareStatement(sql);
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

    private List<Clinician> getCliniciansByTreatmentId(int treatmentId) {
        List<Clinician> clinicians = new ArrayList<>();
        try {
            String sql = "SELECT clinician_id FROM TreatmentClinicians WHERE treatment_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
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

    private List<PatientTreatment> getAppointmentsByTreatmentId(int treatmentId) {
        List<PatientTreatment> appointments = new ArrayList<>();
        try {
            String sql = "SELECT patient_id, appointment_date FROM PatientTreatments WHERE treatment_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, treatmentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int patientId = rs.getInt("patient_id");
                Date appointmentDate = rs.getDate("appointment_date");
                PatientTreatment appointment = new PatientTreatment(patientId, appointmentDate.toLocalDate());
                appointments.add(appointment);
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
            PreparedStatement ps = c.prepareStatement(sql);
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
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, treatmentId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error clearing appointments for treatment");
            e.printStackTrace();
        }
    }


}