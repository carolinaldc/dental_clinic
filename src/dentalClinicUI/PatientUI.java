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
    
    
    public void modifyPatient() {
        try {
            viewPatientsList();
            System.out.println("Enter ID of patient to modify:");
            int id = Integer.parseInt(reader.readLine());

            Patient patient = patientManager.getPatientById(id);
            if (patient == null) {
                System.out.println("Patient not found.");
                return;
            }

            System.out.println("What do you want to modify:");
            System.out.println("1. Name");
            System.out.println("2. Surname");
            System.out.println("3. Phone");
            System.out.println("4. Email");
            System.out.println("5. Credit Card");

            int choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                    System.out.println("Enter new name (" + patient.getName() + "):");
                    String newName = reader.readLine();
                    if (!newName.trim().isEmpty()) {
                        patientManager.updatePatient(id, "name", newName);
                    }
                    break;
                case 2:
                    System.out.println("Enter new surname (" + patient.getSurname() + "):");
                    String newSurname = reader.readLine();
                    if (!newSurname.trim().isEmpty()) {
                        patientManager.updatePatient(id, "surname", newSurname);
                    }
                    break;
                case 3:
                    System.out.println("Enter new phone (" + patient.getPhone() + "):");
                    String newPhone = reader.readLine();
                    if (!newPhone.trim().isEmpty()) {
                        patientManager.updatePatient(id, "phone", newPhone);
                    }
                    break;
                case 4:
                    System.out.println("Enter new email (" + patient.getEmail() + "):");
                    String newEmail = reader.readLine();
                    if (!newEmail.trim().isEmpty()) {
                        patientManager.updatePatient(id, "email", newEmail);
                    }
                    break;
                case 5:
                    System.out.println("Enter new credit card (" + patient.getCredit_card() + "):");
                    String newCard = reader.readLine();
                    if (!newCard.trim().isEmpty()) {
                        patientManager.updatePatient(id, "credit_card", newCard);
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            System.out.println("Patient updated.");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error modifying patient.");
            e.printStackTrace();
        }
    }

    
    
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
