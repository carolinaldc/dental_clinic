package dentalClinicUI;

import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.PatientTreatment;
import dentalClinicPOJOS.Treatment;
import dentalClinicIFaces.ClinicianManager;
import dentalClinicIFaces.PatientManager;
import dentalClinicIFaces.PatientsTreatmentManager;
import dentalClinicIFaces.TreatmentManager;
import dentalClinicJDBC.JDBCManager;
import dentalClinicJDBC.JDBCPatientTreatmentManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppointmentUI {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static PatientsTreatmentManager appointmentManager = new JDBCPatientTreatmentManager(new JDBCManager());
    
    private PatientManager patientManager;
    private ClinicianManager clinicianManager;
    private TreatmentManager treatmentManager;
    
    public AppointmentUI(PatientManager patientManager, ClinicianManager clinicianManager, TreatmentManager treatmentManager) {
    	this.patientManager = patientManager;
    	this.clinicianManager = clinicianManager ;
    	this.treatmentManager = treatmentManager;
    }


    public void bookAppointment() {
        try {
            if (patientManager.listPatients().isEmpty()) {
                System.out.println("No Patients available. Please add patients before booking an appointment.");
                return;
            }

            if (treatmentManager.getAllTreatments().isEmpty()) {
                System.out.println("No Treatments available. Please add treatments before booking an appointment.");
                return;
            }

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
            
            appointmentManager.addPatientTreatment(newAppointment); 

            System.out.println("Appointment successfully booked!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void modifyAppointment() {
        try {
            List<PatientTreatment> appointments = appointmentManager.getAllPatientTreatments();
        	if (appointments != null && !appointments.isEmpty()) {
        		for (PatientTreatment appointment : appointments) {
                    System.out.println(appointment);
                }
        		
        		System.out.println("Enter Appointment ID to modify:");
                int appointmentId = Integer.parseInt(reader.readLine());

                PatientTreatment appointmentToModify = appointmentManager.getPatientTreatmentById(appointmentId);
                int choice=0;
        		try {
        			System.out.println("What do you want to modify: ");
    	            System.out.println("1. patient ");
    	            System.out.println("2. clinician ");
    	            System.out.println("3. treatment");
    	            System.out.println("4. date");
    	            System.out.println("5. comment");
    				
    				choice = Integer.parseInt(reader.readLine());

    				switch (choice) {
    					case 1:
    			            System.out.println("Enter new patient ID:");
    			            //viewListPatients();
    			            //Patient newPatient = reader.readLine();
    			            //if (!newPatient.trim().isEmpty()) appointmentToModify.setPatient(newPatient);
    						break;
    					case 2:
    			            System.out.println("Enter new treatment ID:");
    			            //viewListClinicians();
    			            //Clinician newClinician = reader.readLine();
    			            //if (!newClinician.trim().isEmpty()) appointmentToModify.setClinician(newClinician);
    						break;
    					case 3:
    			            System.out.println("Enter new treatment ID:");
    			            //viewListTreatments();
    			            //Treatment newTreatment = reader.readLine();
    			            //if (!newTreatment.trim().isEmpty()) appointmentToModify.setTreatment(newTreatment);
    						break;
    					case 4:
    			            System.out.println("Enter new date for the appointment (yyyy-MM-dd):");
    			            String newDateStr = reader.readLine();
    			            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    			            Date newDate = sdf.parse(newDateStr);
    			            //if (!newDate.trim().isEmpty()) appointmentToModify.setDate(newDate);
    						break;
    					case 5:
    			            System.out.println("Enter new comment for the appointment:");
    			            String newComment = reader.readLine();
    			            if (!newComment.trim().isEmpty()) appointmentToModify.setComment(newComment);
    						break;
    				}
        		}catch(Exception e){
        			e.printStackTrace();
        		}
            } else {
                System.out.println("No appointments found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelAppointment() {
        try {
        	List<PatientTreatment> appointments = appointmentManager.getAllPatientTreatments();
            if (appointments != null && !appointments.isEmpty()) {
                 for (PatientTreatment appointment : appointments) {
                     System.out.println(appointment);
                 }
                 System.out.println("Enter Appointment ID:");
                 int appointmentId = Integer.parseInt(reader.readLine());

                 PatientTreatment appointmentToCancel = appointmentManager.getPatientTreatmentById(appointmentId);
                 appointmentManager.removePatientTreatment(appointmentToCancel);
                 System.out.println("Appointment successfully canceled!");
             } else {
                 System.out.println("No appointments found.");
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void viewAppointmentsList() {
        try {
           List<PatientTreatment> appointments = appointmentManager.getAllPatientTreatments();
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
