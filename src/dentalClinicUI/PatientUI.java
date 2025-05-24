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
    private Patient currentPatient;
    private BufferedReader reader;

    public PatientUI(PatientManager patientManager) {
        this.patientManager = patientManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void setCurrentPatient(Patient patient) {
        this.currentPatient = patient;
    }


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
        if (currentPatient == null) {
            System.out.println("No patient logged in.");
            return;
        }
        try {
            patientManager.deletePatient(currentPatient.getPatient_id());
            System.out.println("Your patient profile has been deleted.");
            currentPatient = null;
        } catch (Exception e) {
            System.out.println("Error deleting patient.");
            e.printStackTrace();
        }
    }

    
    public void modifyPatient() {
        if (currentPatient == null) {
            System.out.println("No patient logged in.");
            return;
        }

        try {
            Patient patient = currentPatient;

            System.out.println("What do you want to modify:");
            System.out.println("1. Name");
            System.out.println("2. Surname");
            System.out.println("3. Phone");
            System.out.println("4. Credit Card");

            int choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                    System.out.println("Enter new name (" + patient.getName() + "):");
                    String newName = reader.readLine();
                    if (!newName.trim().isEmpty()) {
                        patientManager.updatePatient(patient.getPatient_id(), "name", newName);
                    }
                    break;
                case 2:
                    System.out.println("Enter new surname (" + patient.getSurname() + "):");
                    String newSurname = reader.readLine();
                    if (!newSurname.trim().isEmpty()) {
                        patientManager.updatePatient(patient.getPatient_id(), "surname", newSurname);
                    }
                    break;
                case 3:
                    System.out.println("Enter new phone (" + patient.getPhone() + "):");
                    String newPhone = reader.readLine();
                    if (!newPhone.trim().isEmpty()) {
                        patientManager.updatePatient(patient.getPatient_id(), "phone", newPhone);
                    }
                    break;
                case 4:
                    System.out.println("Enter new credit card (" + patient.getCredit_card() + "):");
                    String newCard = reader.readLine();
                    if (!newCard.trim().isEmpty()) {
                        patientManager.updatePatient(patient.getPatient_id(), "credit_card", newCard);
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
