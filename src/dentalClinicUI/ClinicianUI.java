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
    
    
    //modifyClinician();
    
    
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
