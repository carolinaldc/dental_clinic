package dentalClinicUI;

import dentalClinicIFaces.PatientManager;
import dentalClinicPOJOS.Patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.List;

public class PatientUI {
    private PatientManager patientManager;
    private BufferedReader reader;

    public PatientUI(PatientManager patientManager) {
        this.patientManager = patientManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void addPatient() {
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

            Patient patient = new Patient(name, surname, dob, phone, creditCard);
            patientManager.addPatient(patient);
            System.out.println("Patient added successfully.");

        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error adding patient.");
            e.printStackTrace();
        }
    }

    public void viewPatientsList() {
        List<Patient> patients = patientManager.listPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
        } else {
            for (Patient p : patients) {
                System.out.println(p);
            }
        }
    }

    public void deletePatient() {
        try {
            viewPatientsList();
            System.out.println("Enter ID of patient to delete:");
            int id = Integer.parseInt(reader.readLine());

            patientManager.deletePatient(id);
            System.out.println("Patient deleted.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    public void modifyPatient() {
        try {
            viewPatientsList();
            System.out.println("Enter ID of patient to modify:");
            int id = Integer.parseInt(reader.readLine());

            Patient patient = patientManager.getPatientByid(id);
            if (patient == null) {
                System.out.println("Patient not found.");
                return;
            }
                        
            int choice=0;
    		try {
    			System.out.println("What do you want to modify: ");
	            System.out.println("1. name ");
	            System.out.println("2. surname");
	            System.out.println("3. phone");
	            System.out.println("4. email");
	            System.out.println("5. credit card");
				
				choice = Integer.parseInt(reader.readLine());

				switch (choice) {
					case 1:
			            System.out.println("Enter new name (" + patient.getName() + "):");
			            String newName = reader.readLine();
			            if (!newName.trim().isEmpty()) patient.setName(newName);
						break;
					case 2:
			            System.out.println("Enter new surname (" + patient.getSurname() + "):");
			            String newSurname = reader.readLine();
			            if (!newSurname.trim().isEmpty()) patient.setSurname(newSurname);
						break;
					case 3:
			            System.out.println("Enter new phone (" + patient.getPhone() + "):");
			            String newPhone = reader.readLine();
			            if (!newPhone.trim().isEmpty()) patient.setPhone(Integer.parseInt(newPhone));
						break;
					case 4:
			            System.out.println("Enter new email (" + patient.getEmail() + "):");
			            String newEmail = reader.readLine();
			            if (!newEmail.trim().isEmpty()) patient.setEmail(newEmail);
					case 5:
			            System.out.println("Enter new credit card (9 digits)(" + patient.getCredit_card() + "):");
			            String newCard = reader.readLine();
			            if (!newCard.trim().isEmpty()) patient.setCredit_card(Integer.parseInt(newCard));
						break;
				}
    		}catch(Exception e){
    			e.printStackTrace();
    		}

            patientManager.updatePatient(patient);
            System.out.println("Patient updated.");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error modifying patient.");
        }
    }
}
