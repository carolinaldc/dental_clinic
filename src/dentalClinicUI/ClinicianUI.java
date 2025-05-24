package dentalClinicUI;

import dentalClinicIFaces.ClinicianManager;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ClinicianUI {
    private ClinicianManager clinicianManager;
    private Clinician currentClinician;
    private BufferedReader reader;

    public ClinicianUI(ClinicianManager clinicianManager) {
        this.clinicianManager = clinicianManager;
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

            int choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                    System.out.println("Enter new name (" + clinician.getName() + "):");
                    String newName = reader.readLine();
                    if (!newName.trim().isEmpty()) {
                        clinicianManager.updateClinician(clinician.getClinician_id(), "name", newName);
                    }
                    break;
                case 2:
                    System.out.println("Enter new surname (" + clinician.getSurname() + "):");
                    String newSurname = reader.readLine();
                    if (!newSurname.trim().isEmpty()) {
                        clinicianManager.updateClinician(clinician.getClinician_id(), "surname", newSurname);
                    }
                    break;
                case 3:
                    System.out.println("Enter new phone (" + clinician.getPhone() + "):");
                    String newPhone = reader.readLine();
                    if (!newPhone.trim().isEmpty()) {
                        clinicianManager.updateClinician(clinician.getClinician_id(), "phone", newPhone);
                    }
                    break;
                case 4:
                    System.out.println("Enter new specialty (" + clinician.getSpecialty() + "):");
                    String newSpecialty = reader.readLine();
                    if (!newSpecialty.trim().isEmpty()) {
                        clinicianManager.updateClinician(clinician.getClinician_id(), "specialty", newSpecialty);
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
