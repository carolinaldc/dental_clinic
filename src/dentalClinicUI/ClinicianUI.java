package dentalClinicUI;

import dentalClinicIFaces.ClinicianManager;
import dentalClinicIFaces.UserManager;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ClinicianUI {
    private ClinicianManager clinicianManager;
    private Clinician currentClinician;
    private UserManager usermanager;
    private BufferedReader reader;

    public ClinicianUI(ClinicianManager clinicianManager, UserManager usermanager) {
        this.clinicianManager = clinicianManager;
        this.usermanager = usermanager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public void setCurrentClinician(Clinician clinician) {
        this.currentClinician = clinician;
    }
    
    public void addClinician(String email) {
        try {
            System.out.println("Enter name:");
            String name = reader.readLine();

            System.out.println("Enter surname:");
            String surname = reader.readLine();

            System.out.println("Enter speciality:");
            String speciality = reader.readLine();
            
            System.out.println("Enter phone number:");
            int phone = Integer.parseInt(reader.readLine());

            List<Appointment> appointments = new ArrayList<>(); //adds appointment later on

            Clinician clinician = new Clinician(name, surname, speciality, phone, email, appointments);
            clinicianManager.addClinician(clinician);
            System.out.println("Clinician added successfully.");

        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error adding patient.");
            e.printStackTrace();
        }
    }
    
    public void deleteClinician() {
        if (currentClinician == null) {
            System.out.println("No clinician logged in.");
            return;
        }
        try {
            clinicianManager.deleteClinician(currentClinician.getClinician_id());
            System.out.println("Your clinician profile has been deleted.");
            currentClinician = null;
        } catch (Exception e) {
            System.out.println("Error deleting clinician.");
            e.printStackTrace();
        }
    }
    
    public void deleteClinicianByEmail() {
        if (currentClinician == null) {
            System.out.println("No clinician logged in.");
            return;
        }
        try {
        	clinicianManager.deleteClinicianByEmail(currentClinician.getEmail());
            System.out.println("Your clinian profile has been deleted.");
            currentClinician = null;
        } catch (Exception e) {
            System.out.println("Error deleting clinician.");
            e.printStackTrace();
        }
    }

    
    public void modifyClinician() {
        if (currentClinician == null) {
            System.out.println("No clinician logged in.");
            return;
        }

        try {
            Clinician clinician = currentClinician;

            System.out.println("What do you want to modify:");
            System.out.println("1. Name");
            System.out.println("2. Surname");
            System.out.println("3. Phone");
            System.out.println("4. Specialty");
            System.out.println("5. Email");
            System.out.println("6. Password");
            

            int choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                	System.out.println("Enter new name (" + clinician.getName() + "):");
                    String newName = reader.readLine().trim();
                    if (!newName.isEmpty()) {
                        clinicianManager.updateName(clinician.getClinician_id(), newName);
                        clinician.setName(newName);
                    }
                    break;
                case 2:
                	System.out.println("Enter new surname (" + clinician.getSurname() + "):");
                    String newSurname = reader.readLine().trim();
                    if (!newSurname.isEmpty()) {
                        clinicianManager.updateSurname(clinician.getClinician_id(), newSurname);
                        clinician.setSurname(newSurname);
                    }
                    break;
                case 3:
                	System.out.println("Enter new phone (" + clinician.getPhone() + "):");
                    String newPhoneInput = reader.readLine().trim();
                    if (!newPhoneInput.isEmpty()) {
                        try {
                            int newPhone = Integer.parseInt(newPhoneInput);
                            clinicianManager.updatePhone(clinician.getClinician_id(), newPhone);
                            clinician.setPhone(newPhone); // update local object
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid phone number. Please enter numeric digits only.");
                        }
                    }
                    break;
                case 4:
                	System.out.println("Enter new specialty (" + clinician.getSpecialty() + "):");
                    String newSpecialty = reader.readLine().trim();
                    if (!newSpecialty.isEmpty()) {
                        clinicianManager.updateSpecialty(clinician.getClinician_id(), newSpecialty);
                        clinician.setSpeciality(newSpecialty);
                    }
                    break;
                case 5:
                	System.out.println("Enter new email (" + clinician.getEmail() + "):");
                    String newEmail = reader.readLine();

                    if (!newEmail.trim().isEmpty()) {
                        // Check if email already exists
                        if (usermanager.getUserByEmail(newEmail) != null) {
                            System.out.println("Email already in use. Try a different one.");
                        } else {
                            // Update in the users table
                            User u = usermanager.getUserByEmail(clinician.getEmail());
                            usermanager.changeEmail(u, newEmail);  

                            clinician.setEmail(newEmail);
                            clinicianManager.updateClinicianEmail(clinician.getClinician_id(), newEmail);
                            
                            currentClinician = clinician;
                            System.out.println("Email updated successfully.");
                        }
                    }
                    break;
                case 6:
                	System.out.println("Enter new password:");
                    String newPassword = reader.readLine();
                    
                    if (!newPassword.trim().isEmpty()) {
                        User u = usermanager.getUserByEmail(clinician.getEmail());
                        if (u != null) {
                            usermanager.changePassword(u, newPassword);
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
                    return;
            }
            System.out.println("Clinician updated.");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error modifying clinician.");
            e.printStackTrace();
        }
    }

    
    
    public void viewCliniciansList() {
        try {
           List<Clinician> clinicians = clinicianManager.getListOfClinicians();
           if (clinicians != null && !clinicians.isEmpty()) {
                for (Clinician clinician : clinicians) {
                    System.out.println(clinician);
                }
            } else {
                System.out.println("No clinicians found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
