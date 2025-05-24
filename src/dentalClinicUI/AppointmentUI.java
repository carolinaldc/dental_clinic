package dentalClinicUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import dentalClinicPOJOS.Role;
import dentalClinicPOJOS.Treatment;

public class AppointmentUI {
	private static AppointmentManager appointmentManager;
	private static PatientManager patientManager;
	private static ClinicianManager clinicianManager;
	private static TreatmentManager treatmentManager;
	private static TreatmentUI treatmentUI;
	private static PatientUI patientUI;
	private static ClinicianUI clinicianUI;

    private BufferedReader reader;


    public AppointmentUI(AppointmentManager appointmentManager) {
        this.appointmentManager = appointmentManager;
        this.patientUI = new PatientUI(patientManager);
        this.clinicianUI = new ClinicianUI(clinicianManager);
        this.treatmentUI = new TreatmentUI(treatmentManager);
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }


    public AppointmentUI(AppointmentManager appointmentManager, PatientManager patientManager, ClinicianManager clinicianManager, TreatmentManager treatmentManager) {
        this.appointmentManager = appointmentManager;
        this.patientManager = patientManager;
        this.clinicianManager = clinicianManager;
        this.treatmentManager = treatmentManager;
        this.patientUI = new PatientUI(patientManager);
        this.clinicianUI = new ClinicianUI(clinicianManager);
        this.treatmentUI = new TreatmentUI(treatmentManager);
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public void addAppointment(String email, Role role) throws ParseException {
        try {
            System.out.println("Enter appointment date (yyyy-MM-dd):");
            String dateString = reader.readLine();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateString);

            System.out.println("Enter comment:");
            String comment = reader.readLine();

            Patient patient = null;
            Clinician clinician = null;

            if ("Patient".equalsIgnoreCase(role.getDescription())) {
                clinicianUI.viewCliniciansList();
                System.out.println("Enter clinician id:");
                int clinicianId = Integer.parseInt(reader.readLine());
                clinician = clinicianManager.getClinicianById(clinicianId);

                if (clinician == null) {
                    System.out.println("Clinician not found with id: " + clinicianId);
                    return;
                }
                patient = patientManager.getPatientByEmail(email);
                if (patient == null) {
                    System.out.println("Logged-in patient not found with email: " + email);
                    return;
                }
            } else if ("Clinician".equalsIgnoreCase(role.getDescription())) {
                patientUI.viewPatientsList();
                System.out.println("Enter patient id:");
                int patientId = Integer.parseInt(reader.readLine());
                patient = patientManager.getPatientById(patientId);

                if (patient == null) {
                    System.out.println("Patient not found with id: " + patientId);
                    return;
                }
                clinician = clinicianManager.getClinicianByEmail(email);
                if (clinician == null) {
                    System.out.println("Logged-in clinician not found with email: " + email);
                    return;
                }
            } else {
                System.out.println("Role not recognized.");
                return;
            }

            treatmentUI.viewTreatmentsList();
            System.out.println("Enter treatment id:");
            int treatmentId = Integer.parseInt(reader.readLine());
            Treatment treatment = treatmentManager.getTreatmentById(treatmentId);

            if (treatment == null) {
                System.out.println("Treatment not found with id: " + treatmentId);
                return;
            }
            
            Appointment appointment = new Appointment(date, comment, patient, treatment, clinician);
            appointmentManager.addAppointment(appointment);

            System.out.println("Appointment added successfully.");

        } catch (IOException e) {
            System.out.println("Error reading input.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        }
    }

/*
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


            clinicianUI.viewCliniciansList();
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

	*/
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


	public String getListOfAppointments(String email, Role role) {
		// TODO Auto-generated method stub
		return null;
	}
}
