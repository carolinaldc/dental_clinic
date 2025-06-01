package dentalClinicUI;

import dentalClinicIFaces.PatientManager;
import dentalClinicIFaces.UserManager;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PatientUI {
    private PatientManager patientManager;
    private UserManager userManager;
    private Patient currentPatient;
    private BufferedReader reader;

    public PatientUI(PatientManager patientManager, UserManager usermanager) {
        this.patientManager = patientManager;
        this.userManager = usermanager;
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
    
    public void deletePatientByEmail() {
        if (currentPatient == null) {
            System.out.println("No patient logged in.");
            return;
        }
        try {
            patientManager.deletePatientByEmail(currentPatient.getEmail());
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
            System.out.println("5. Email");
            System.out.println("6. Password");

            int choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                	System.out.println("Enter new name (" + patient.getName() + "):");
                    String newName = reader.readLine().trim();
                    if (!newName.isEmpty()) {
                        patientManager.updateName(patient.getPatient_id(), newName);
                        patient.setName(newName);
                    }
                    break;
                case 2:
                	System.out.println("Enter new surname (" + patient.getSurname() + "):");
                    String newSurname = reader.readLine().trim();
                    if (!newSurname.isEmpty()) {
                        patientManager.updateSurname(patient.getPatient_id(), newSurname);
                        patient.setSurname(newSurname);
                    }
                    break;
                case 3:
                	System.out.println("Enter new phone (" + patient.getPhone() + "):");
                    String newPhoneInput = reader.readLine().trim();
                    if (!newPhoneInput.isEmpty()) {
                        try {
                            int newPhone = Integer.parseInt(newPhoneInput);
                            patientManager.updatePhone(patient.getPatient_id(), newPhone);
                            patient.setPhone(newPhone); // update local object
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid phone number. Please enter numeric digits only.");
                        }
                    }
                    break;
                case 4:
                	System.out.println("Enter new credit_card (" + patient.getCredit_card() + "):");
                    String newCreditCardInput = reader.readLine().trim();
                    if (!newCreditCardInput.isEmpty()) {
                        try {
                            int newCreditCard = Integer.parseInt(newCreditCardInput);
                            patientManager.updatePhone(patient.getPatient_id(), newCreditCard);
                            patient.setCredit_card(newCreditCard); // update local object
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid phone number. Please enter numeric digits only.");
                        }
                    }
                    break;
                case 5:
                	System.out.println("Enter new email (" + patient.getEmail() + "):");
                    String newEmail = reader.readLine();

                    if (!newEmail.trim().isEmpty()) {
                        // Check if email already exists
                        if (userManager.getUserByEmail(newEmail) != null) {
                            System.out.println("Email already in use. Try a different one.");
                        } else {
                            // Update in the users table
                            User u = userManager.getUserByEmail(patient.getEmail());
                            userManager.changeEmail(u, newEmail);  

                            patient.setEmail(newEmail);
                            patientManager.updatePatientEmail(patient.getPatient_id(), newEmail);
                            
                            currentPatient = patient;
                            System.out.println("Email updated successfully.");
                        }
                    }
                    break;
                case 6:
                	System.out.println("Enter new password:");
                    String newPassword = reader.readLine();
                    
                    if (!newPassword.trim().isEmpty()) {
                        User u = userManager.getUserByEmail(patient.getEmail());
                        if (u != null) {
                            userManager.changePassword(u, newPassword);
                            System.out.println("Password updated successfully.");
                        } else {
                            System.out.println("User not found.");
                        }
                    } else {
                        System.out.println("Password cannot be empty.");
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
