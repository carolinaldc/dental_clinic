package dentalClinicUI;

import dentalClinicIFaces.ClinicianManager;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Clinician;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ClinicianUI {
    private ClinicianManager clinicianManager;

    private BufferedReader reader;

    public ClinicianUI(ClinicianManager clinicianManager) {
        this.clinicianManager = clinicianManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
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
        try {
        	viewCliniciansList();
            System.out.println("Enter ID of clinician to delete:");
            int id = Integer.parseInt(reader.readLine());

            clinicianManager.deleteClinician(id);
            System.out.println("Client deleted");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input");
        }
    }
    
    
    public void modifyClinician() {
        try {
        	viewCliniciansList();
            System.out.println("Enter ID of clinician to modify:");
            int id = Integer.parseInt(reader.readLine());

            Clinician clinician = clinicianManager.getClinicianByid(id);
            if (clinician == null) {
                System.out.println("Clinician not found.");
                return;
            }

            int choice=0;
    		try {
    			System.out.println("What do you want to modify: ");
	            System.out.println("1. name ");
	            System.out.println("2. surname");
	            System.out.println("3. phone");
	            System.out.println("4. email");
	            System.out.println("5. specialty");
				
				choice = Integer.parseInt(reader.readLine());

				switch (choice) {
					case 1:
			            System.out.println("Enter new name (" + clinician.getName() + "):");
			            String newName = reader.readLine();
			            if (!newName.trim().isEmpty()) clinician.setName(newName);
						break;
					case 2:
			            System.out.println("Enter new surname (" + clinician.getSurname() + "):");
			            String newSurname = reader.readLine();
			            if (!newSurname.trim().isEmpty()) clinician.setSurname(newSurname);
						break;
					case 3:
			            System.out.println("Enter new phone (" + clinician.getPhone() + "):");
			            String newPhone = reader.readLine();
			            if (!newPhone.trim().isEmpty()) clinician.setPhone(Integer.parseInt(newPhone));
						break;
					case 4:
			            System.out.println("Enter new email (" + clinician.getEmail() + "):");
			            String newEmail = reader.readLine();
			            if (!newEmail.trim().isEmpty()) clinician.setEmail(newEmail);
					case 5:
			            System.out.println("Enter new specialty (" + clinician.getSpeciality() + "):");
			            String newSpeciality = reader.readLine();
			            if (!newSpeciality.trim().isEmpty()) clinician.setSpeciality(newSpeciality);
						break;
				}
    		}catch(Exception e){
    			e.printStackTrace();
    		}

    		clinicianManager.updateClinician(clinician.getClinician_id());
    		System.out.println("Clinician updated.");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error modifying clinician.");
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
