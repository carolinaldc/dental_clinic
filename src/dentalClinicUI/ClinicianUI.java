package dentalClinicUI;

import dentalClinicIFaces.ClinicianManager;
import dentalClinicPOJOS.Clinician;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.List;

public class ClinicianUI {
    private ClinicianManager clinicianManager;

    private BufferedReader reader;

    public ClinicianUI(ClinicianManager clinicianManager) {
        this.clinicianManager = clinicianManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public void addClinician() {
        try {
            System.out.println("Enter name:");
            String name = reader.readLine();

            System.out.println("Enter surname:");
            String surname = reader.readLine();

            System.out.println("Enter speciality:");
            String speciality = reader.readLine();
            
            System.out.println("Enter phone number:");
            int phone = Integer.parseInt(reader.readLine());

            Clinician clinician = new Clinician(name, surname, speciality, phone);
            clinicianManager.addClinician(clinician);
            System.out.println("Clinician added successfully.");

        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error adding patient.");
            e.printStackTrace();
        }
    }
    
    public void viewCliniciansList() {
        List<Clinician> clinicians = clinicianManager.listClinicians();
        if (clinicians.isEmpty()) {
            System.out.println("No clinicians found.");
        } else {
            for (Clinician c : clinicians) {
                System.out.println(c);
            }
        }
    }
    
    public void deleteClinician() {
        try {
        	viewCliniciansList();
            System.out.println("Enter ID of clinician to delete:");
            int id = Integer.parseInt(reader.readLine());

            clinicianManager.deleteClinician(id);
            System.out.println("Clinician deleted.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    public void modifyClinician() {
        try {
        	viewCliniciansList();
            System.out.println("Enter ID of patient to modify:");
            int id = Integer.parseInt(reader.readLine());

            Clinician clinician = clinicianManager.getClinicianByid(id);
            if (clinician == null) {
                System.out.println("Patient not found.");
                return;
            }

            System.out.println("Enter new name (" + clinician.getName() + "):");
            String newName = reader.readLine();
            if (!newName.trim().isEmpty()) clinician.setName(newName);

            System.out.println("Enter new surname (" + clinician.getSurname() + "):");
            String newSurname = reader.readLine();
            if (!newSurname.trim().isEmpty()) clinician.setSurname(newSurname);


            System.out.println("Enter new specialty (" + clinician.getSpeciality() + "):");
            String newSpeciality = reader.readLine();
            if (!newSpeciality.trim().isEmpty()) clinician.setSpeciality(newSpeciality);
            
            System.out.println("Enter new phone (" + clinician.getPhone() + "):");
            String newPhone = reader.readLine();
            if (!newPhone.trim().isEmpty()) clinician.setPhone(Integer.parseInt(newPhone));

            System.out.println("Enter new email (" + clinician.getEmail() + "):");
            String newEmail = reader.readLine();
            if (!newEmail.trim().isEmpty()) clinician.setEmail(newEmail);

            clinicianManager.updateClinician(clinician);
            System.out.println("Clinician updated.");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error modifying clinician.");
        }
    }
}
