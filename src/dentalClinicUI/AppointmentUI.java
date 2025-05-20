package dentalClinicUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dentalClinicIFaces.AppointmentManager;
import dentalClinicIFaces.ClinicianManager;
import dentalClinicIFaces.PatientManager;
import dentalClinicIFaces.TreatmentManager;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.Treatment;

public class AppointmentUI {
	private static AppointmentManager appointmentManager;
	private static PatientManager patientManager;
	private static ClinicianManager clinicianManager;
	private static TreatmentManager treatmentManager;
	private static TreatmentUI treatmentUI;
	private static PatientUI patientUI;
	private static ClinicianUI cliniciantUI;

    private BufferedReader reader;

	
	public void addAppointment() throws ParseException {
        try {
            System.out.println("Enter appointment date (yyyy-MM-dd):");
            String dateString = reader.readLine();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateString); //ParseException es para date

            System.out.println("Enter comment:");
            String comment = reader.readLine();

            patientUI.viewPatientsList();
            System.out.println("Enter patient id:");
            int patient_id = Integer.parseInt(reader.readLine());
            Patient patient = patientManager.getPatientById(patient_id);


            cliniciantUI.viewCliniciansList();
            System.out.println("Enter clinician id:");
            int clinicianId = Integer.parseInt(reader.readLine());
            Clinician clinician = clinicianManager.getClinicianByid(clinicianId); 

            treatmentUI.viewTreatmentsList();
            System.out.println("Enter treatment id:");
            int treatmentId = Integer.parseInt(reader.readLine());
            Treatment treatment = treatmentManager.getTreatmentById(treatmentId); 

            
            Appointment appointment = new Appointment(date, comment, patient, treatment, clinician);
            appointmentManager.addAppointment(appointment);

            System.out.println("Appointment added successfully.");

        } catch (IOException e) {
            System.out.println("Error reading input.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number for price.");
        }
    }

	
    public void deleteAppointment() { 
        try {
        	viewAppointmentsList();
            System.out.println("Enter ID of appointment to delete:");
            int id = Integer.parseInt(reader.readLine());

            appointmentManager.deleteAppointment(id);
            System.out.println("Appointment deleted.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
	

    //modifyAppointment();
    
    public void viewAppointmentsList() {
        try {
           List<Appointment> appointments = appointmentManager.getListOfAppointments();
           if (appointments != null && !appointments.isEmpty()) {
                for (Appointment appointment : appointments) {
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
