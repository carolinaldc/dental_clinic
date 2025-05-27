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
import dentalClinicIFaces.MaterialManager;
import dentalClinicIFaces.PatientManager;
import dentalClinicIFaces.TreatmentManager;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.Role;
import dentalClinicPOJOS.Treatment;

public class AppointmentUI {
	private  AppointmentManager appointmentManager;
	private  PatientManager patientManager;
	private  ClinicianManager clinicianManager;
	private  TreatmentManager treatmentManager;
	private  MaterialManager materialManager;
	private  TreatmentUI treatmentUI;
	private  PatientUI patientUI;
	private  ClinicianUI clinicianUI;
	private  MaterialUI materialUI;

    private BufferedReader reader;

    public AppointmentUI(AppointmentManager appointmentManager, PatientUI patientUI, ClinicianUI clinicianUI, BufferedReader reader) {
        this.appointmentManager = appointmentManager;
        this.patientUI = patientUI;
        this.clinicianUI = clinicianUI;
        this.reader = reader;
    }

    public AppointmentUI(AppointmentManager appointmentManager, PatientManager patientManager, ClinicianManager clinicianManager, TreatmentManager treatmentManager, BufferedReader reader) {
		this.appointmentManager = appointmentManager;
		this.patientManager = patientManager;
		this.clinicianManager = clinicianManager;
		this.treatmentManager = treatmentManager;
		this.patientUI = new PatientUI(patientManager);
		this.clinicianUI = new ClinicianUI(clinicianManager);
		this.treatmentUI = new TreatmentUI(treatmentManager, materialManager, materialUI, reader);
		this.reader = reader;
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

            //treatmentUI.viewTreatmentsList();
            if (!treatmentUI.viewTreatmentsList()) {
                return;
            }
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

  //modifyAppointment();

    public void deleteAppointment(String email, Role role) { 
        try {
            if (!viewAppointmentsList(email, role)) {
                return;//no appointemnts found
            }

            System.out.println("Enter ID of appointment to delete:");
            int id = Integer.parseInt(reader.readLine());

            appointmentManager.deleteAppointment(id);
            System.out.println("Appointment deleted.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

	


    
    public boolean viewAppointmentsList(String email, Role role) {
        try {
            List<Appointment> appointments = appointmentManager.getListOfAppointments(email, role);
            if (appointments != null && !appointments.isEmpty()) {
                for (Appointment appointment : appointments) {
                    System.out.println(appointment);
                }
                return true;
            } else {
                System.out.println("No appointments found.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }


}
