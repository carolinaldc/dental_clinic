package dentalClinicUI;

import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.PatientTreatment;
import dentalClinicPOJOS.Treatment;
import dentalClinicIFaces.ClinicianManager;
import dentalClinicIFaces.PatientManager;
import dentalClinicIFaces.PatientsTreatmentManager;
import dentalClinicJDBC.JDBCManager;
import dentalClinicJDBC.JDBCPatientTreatmentManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppointmentUI {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static PatientsTreatmentManager treatmentManager = new JDBCPatientTreatmentManager(new JDBCManager());
    
    private PatientManager patientManager;
    private ClinicianManager clinicianManager;
    
    public AppointmentUI(PatientManager patientManager, ClinicianManager clinicianManager) {
    	this.patientManager = patientManager;
    	this.clinicianManager = clinicianManager ;;
    }


	public void bookAppointment() {
        try {
            System.out.println("Enter Patient ID:");
            int patientId = Integer.parseInt(reader.readLine());

            System.out.println("Enter Treatment ID:");
            int treatmentId = Integer.parseInt(reader.readLine());

            System.out.println("Enter Appointment Date (yyyy-MM-dd):");
            String dateStr = reader.readLine(); 
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateStr);


            System.out.println("Enter Comments:");
            String comment = reader.readLine();

            Patient patient = new Patient(patientId); 
            Treatment treatment = new Treatment(treatmentId); 

            PatientTreatment newAppointment = new PatientTreatment(comment, date, treatment, patient);
            
            treatmentManager.addPatientTreatment(newAppointment); 

            System.out.println("Appointment successfully booked!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifyAppointment() {
        try {
            System.out.println("Enter Appointment ID to modify:");
            int appointmentId = Integer.parseInt(reader.readLine());

            System.out.println("Enter new date for the appointment (yyyy-MM-dd):");
            String newDateStr = reader.readLine();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = sdf.parse(newDateStr);


            System.out.println("Enter new comment for the appointment:");
            String newComment = reader.readLine();

            PatientTreatment appointmentToModify = treatmentManager.getPatientTreatmentById(appointmentId);
            if (appointmentToModify != null) {
            	appointmentToModify.setDate(newDate);
            	appointmentToModify.setComment(newComment);
            	treatmentManager.updatePatientTreatment(appointmentToModify);
            	System.out.println("Appointment successfully modified!");
            } else {
                System.out.println("Appointment not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelAppointment() {
        try {
            System.out.println("Enter Appointment ID to cancel:");
            int appointmentId = Integer.parseInt(reader.readLine());

            PatientTreatment appointmentToCancel = treatmentManager.getPatientTreatmentById(appointmentId);
            if (appointmentToCancel != null) {
                treatmentManager.removePatientTreatment(appointmentToCancel);
                System.out.println("Appointment successfully canceled!");
            } else {
                System.out.println("Appointment not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAppointmentsList() {
        try {
           List<PatientTreatment> appointments = treatmentManager.getAllPatientTreatments();
           if (appointments != null && !appointments.isEmpty()) {
                for (PatientTreatment appointment : appointments) {
                    System.out.println(appointment);
                }
            } else {
                System.out.println("No appointments found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
