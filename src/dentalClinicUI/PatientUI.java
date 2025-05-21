package dentalClinicUI;

import dentalClinicIFaces.PatientManager;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PatientUI {
    private PatientManager patientManager;
    //private Patient currentPatient;
    private BufferedReader reader;

    public PatientUI(PatientManager patientManager) {
        this.patientManager = patientManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }
    /*
    public void setCurrentPatient(Patient patient) {
        this.currentPatient = patient;
        System.out.println("Current patient set: " + patient.getName() + " (ID: " + patient.getPatient_id() + ")");
    }
    */
    

    public void addPatient(String email) {
        try {
            System.out.println("Enter name:");
            String name = reader.readLine();

            System.out.println("Enter surname:");
            String surname = reader.readLine();

            System.out.println("Enter date of birth (yyyy-mm-dd):");
            Date dob = Date.valueOf(reader.readLine());

            System.out.println("Enter phone number:");
            int phone = Integer.parseInt(reader.readLine());
            
            System.out.println("Enter credit card number (9 digits):");
            int creditCard = Integer.parseInt(reader.readLine());

            List<Appointment> appointments = new ArrayList<>(); //adds appointment later on
           
            Patient patient = new Patient(name, surname, dob, phone, email, creditCard, appointments);
            patientManager.addPatient(patient);
            System.out.println("Patient added successfully.");
            //this.currentPatient = patientManager.getPatient(email); 
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error adding patient.");
            e.printStackTrace();
        }
    }

    public void deletePatient() {
        try {
        	viewPatientsList();
            System.out.println("Enter ID of patient to delete:");
            int id = Integer.parseInt(reader.readLine());

            patientManager.deletePatient(id);
            System.out.println("Patient deleted");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input");
        }
    }
    
    
    //modifyPatient();
    
    
    public void viewPatientsList() {
        try {
           List<Patient> patients = patientManager.getListOfPatients();
           if (patients != null && !patients.isEmpty()) {
                for (Patient patient : patients) {
                    System.out.println(patient);
                }
            } else {
                System.out.println("No patients found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
