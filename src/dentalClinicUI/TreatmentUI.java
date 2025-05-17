package dentalClinicUI;

import dentalClinicIFaces.TreatmentManager;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.PatientTreatment;
import dentalClinicPOJOS.Room;
import dentalClinicPOJOS.Treatment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TreatmentUI {
    private TreatmentManager treatmentManager;
    private BufferedReader reader;

    public TreatmentUI(TreatmentManager treatmentManager) {
        this.treatmentManager = treatmentManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void addTreatment() { //case 1
        try {
            System.out.println("Enter treatment name:");
            String name = reader.readLine();

            System.out.println("Enter description:");
            String desc = reader.readLine();

            System.out.println("Enter price:");
            int price = Integer.parseInt(reader.readLine());

            Room room = chooseRoom(); 
            List<Clinician> clinicians = chooseClinicians(); 

            Treatment treatment = new Treatment(name, desc, price);
            treatment.setRoom(room);
            treatment.setClinician(clinicians);

            treatmentManager.addTreatment(treatment);
            System.out.println("Treatment added.");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading input.");
            e.printStackTrace();
        }
    }
    
    public void deleteTreatment() { //case 3
        try {
            viewTreatmentsList();
            System.out.println("Enter ID of treatment to delete:");
            int id = Integer.parseInt(reader.readLine());

            treatmentManager.deleteTreatment(id);
            System.out.println("Treatment deleted.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    public void modifyTreatment() { // case 2
        try {
            viewTreatmentsList();
            System.out.println("Enter ID of treatment to modify:");
            int id = Integer.parseInt(reader.readLine());

            Treatment treatment = treatmentManager.getTreatmentByid(id);
            if (treatment == null) {
                System.out.println("Treatment not found.");
                return;
            }

            System.out.println("Enter new name (" + treatment.getName() + "):");
            String newName = reader.readLine();
            if (!newName.trim().isEmpty()) treatment.setName(newName);

            System.out.println("Enter new description (" + treatment.getDescription() + "):");
            String newDesc = reader.readLine();
            if (!newDesc.trim().isEmpty()) treatment.setDescription(newDesc);

            System.out.println("Enter new price (" + treatment.getPrice() + "):");
            String newPriceInput = reader.readLine();
            if (!newPriceInput.trim().isEmpty()) {
                treatment.setPrice(Integer.parseInt(newPriceInput));
            }


            treatmentManager.updateTreatment(treatment);
            System.out.println("Treatment updated.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error modifying treatment.");
        }
    }

    
/*
    public void modifyTreatment() {
        try {
            List<Treatment> treatments = treatmentManager.getAllTreatments();
        	if (appointments != null && !treatments.isEmpty()) {
        		for (Treatment treatment : treatments) {
                    System.out.println(treatment);
                }
        		
        		System.out.println("Enter Appointment ID to modify:");
                int treatmentId = Integer.parseInt(reader.readLine());

                Treatment treatmentToModify = treatmentManager.getTreatmentByid(treatmentId);
                int choice=0;
        		try {
        			System.out.println("What do you want to modify: ");
    	            System.out.println("1. patient ");
    	            System.out.println("2. clinician ");
    	            System.out.println("3. treatment");
    	            System.out.println("4. date");
    	            System.out.println("5. comment");
    				
    				choice = Integer.parseInt(reader.readLine());

    				switch (choice) {
    					case 1:
    			            System.out.println("Enter new patient ID:");
    			            //viewListPatients();
    			            //Patient newPatient = reader.readLine();
    			            //if (!newPatient.trim().isEmpty()) appointmentToModify.setPatient(newPatient);
    						break;
    					case 2:
    			            System.out.println("Enter new treatment ID:");
    			            //viewListClinicians();
    			            //Clinician newClinician = reader.readLine();
    			            //if (!newClinician.trim().isEmpty()) appointmentToModify.setClinician(newClinician);
    						break;
    					case 3:
    			            System.out.println("Enter new treatment ID:");
    			            //viewListTreatments();
    			            //Treatment newTreatment = reader.readLine();
    			            //if (!newTreatment.trim().isEmpty()) appointmentToModify.setTreatment(newTreatment);
    						break;
    					case 4:
    			            System.out.println("Enter new date for the appointment (yyyy-MM-dd):");
    			            String newDateStr = reader.readLine();
    			            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    			            Date newDate = sdf.parse(newDateStr);
    			            //if (!newDate.trim().isEmpty()) appointmentToModify.setDate(newDate);
    						break;
    					case 5:
    			            System.out.println("Enter new comment for the appointment:");
    			            String newComment = reader.readLine();
    			            if (!newComment.trim().isEmpty()) appointmentToModify.setComment(newComment);
    						break;
    				}
        		}catch(Exception e){
        			e.printStackTrace();
        		}
            } else {
                System.out.println("No appointments found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    
    public void viewTreatmentsList() {
        try {
           List<Treatment> treatments = treatmentManager.getAllTreatments();
           if (treatments != null && !treatments.isEmpty()) {
                for (Treatment treatment : treatments) {
                    System.out.println(treatment);
                }
            } else {
                System.out.println("No appointments found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    //necesario??
    private Room chooseRoom() {
        return new Room();
    }

    //necesario??
    private List<Clinician> chooseClinicians() {
        return new ArrayList<>();
    }
}

