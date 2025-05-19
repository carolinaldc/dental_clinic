package dentalClinicUI;

import dentalClinicIFaces.TreatmentManager;
import dentalClinicJDBC.JDBCClinicianManager;
import dentalClinicJDBC.JDBCManager;
import dentalClinicJDBC.JDBCPatientManager;
import dentalClinicJDBC.JDBCRoomManager;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Patient;
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

    private JDBCManager manager;
    private JDBCPatientManager patientManager;
    private JDBCClinicianManager clinicianManager;
    private JDBCRoomManager roomManager;



    public TreatmentUI(TreatmentManager treatmentManager, JDBCManager manager) {
        this.treatmentManager = treatmentManager;
        this.manager = manager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.patientManager = new JDBCPatientManager(manager);
        this.clinicianManager = new JDBCClinicianManager(manager);
        this.roomManager = new JDBCRoomManager(manager);
    }
    
    //patientAddtreatment -> name, date, clinician
    public void addTreatment() {
        try {
            System.out.println("Enter treatment name:"); //EXAMPLE: Root canal treatment/ Dental extraction/ Teeth cleaning
            String name = reader.readLine();
            
            System.out.println("Enter description:"); //Example: the patient has sensitive gums, so he'll need more anesthesia
            String description = reader.readLine();

            System.out.println("Enter price:"); //-> only clinician can do that
            int price = Integer.parseInt(reader.readLine());
            
            //hay que añadir date
            
            
            Room room = chooseRoom(); //-> only clinician can do that
            Clinician clinician = chooseClinician();
            Patient patient = choosePatient();

            Treatment treatment = new Treatment(name, description, price);
            treatment.setRoom(room);
            treatment.setClinician(clinician);
            treatment.setPatient(patient);

            treatmentManager.addTreatment(treatment);
            System.out.println("Treatment added successfully.");

        } catch (IOException e) {
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

    //both patients and clinicians can modify their treatment appointment
    //only clinician can modify: price, description, name, room
    public void modifyTreatment() {
        try {
            List<Treatment> treatments = treatmentManager.getAllTreatments();
        	if (treatments != null && !treatments.isEmpty()) {
        		for (Treatment t : treatments) {
                    System.out.println(t);
                }
        		
        		System.out.println("Enter treatment ID to modify:");
                int treatmentId = Integer.parseInt(reader.readLine());

                Treatment treatmentToModify = treatmentManager.getTreatmentByid(treatmentId);
                int choice=0;
        		try {
        			System.out.println("What do you want to modify: ");
    	            System.out.println("1. name "); //-> only clinician can do that
    	            System.out.println("2. description "); //-> only clinician can do that
    	            System.out.println("3. price");  //-> only clinician can do that
    				System.out.println("4. room"); //-> only clinician can do that
    				System.out.println("5. clinician"); 
    				System.out.println("6. patient");
    				//falta date -> both can modify it


    				choice = Integer.parseInt(reader.readLine());

    				switch (choice) {
    					case 1:
    			            System.out.println("Enter new name for the treatment:");
    			            String newName = reader.readLine();
    			            treatmentToModify.setName(newName);
    						break;
    					case 2:
    			            System.out.println("Enter new description for the treatment:");
    			            String newDescription = reader.readLine();
    			            treatmentToModify.setDescription(newDescription);
    						break;
    					case 3:
    			            System.out.println("Enter new price for the treatment:");
    			            int newPrice = Integer.parseInt(reader.readLine());
    			            treatmentToModify.setPrice(newPrice);
    						break;
    					case 4:
    						Room newRoom = chooseRoom(); 
    			            treatmentToModify.setRoom(newRoom);
    			            break;
    					case 5:
    			            Clinician newClinician = chooseClinician();
    			            treatmentToModify.setClinician(newClinician);
    						break;
    					case 6:
    			            Patient newPatient = choosePatient();
    			            treatmentToModify.setPatient(newPatient);
    						break;
    				}
        		}catch(Exception e){
        			e.printStackTrace();
        		}
            } else {
                System.out.println("No treatment found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void patientModifyTreatment() {
        try {
            List<Treatment> treatments = treatmentManager.getAllTreatments();
        	if (treatments != null && !treatments.isEmpty()) {
        		for (Treatment t : treatments) {
                    System.out.println(t);
                }
        		
        		System.out.println("Enter treatment ID to modify:");
                int treatmentId = Integer.parseInt(reader.readLine());

                Treatment treatmentToModify = treatmentManager.getTreatmentByid(treatmentId);
                int choice=0;
        		try {
        			System.out.println("What do you want to modify: ");
    				System.out.println("1. clinician"); 
    				System.out.println("2. date"); //-> not created yet


    				choice = Integer.parseInt(reader.readLine());

    				if(choice == 1) {
    			            Clinician newClinician = chooseClinician();
    			            treatmentToModify.setClinician(newClinician);
    				}else if(choice == 2) {
    					//edit date
    				}
        		}catch(Exception e){
        			e.printStackTrace();
        		}
            } else {
                System.out.println("No treatment found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //need to modify this
    //right now it shows all the treatments, but it should show the treatments of a specific user
    public void viewTreatmentsList() {
        try {
           List<Treatment> treatments = treatmentManager.getAllTreatments();
           if (treatments != null && !treatments.isEmpty()) {
                for (Treatment treatment : treatments) {
                    System.out.println(treatment);
                }
            } else {
                System.out.println("No treatments found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private Room chooseRoom() throws IOException {
        List<Room> rooms = roomManager.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms available. Add rooms before");
            return null;
        }

        System.out.println("Available Rooms:");
        for (Room room : rooms) {
            System.out.println("ID: " + room.getRoom_id() + " - " + room);
        }

        while (true) {
        	System.out.println("Enter the Room ID:");
            int id = Integer.parseInt(reader.readLine());

            for (Room room : rooms) {
                if (room.getRoom_id() == id) {
                    return room;
                }
            }
            System.out.println("Invalid Room ID. Please try again.");
        }
    }


    private Clinician chooseClinician() throws IOException {
        List<Clinician> clinicians = clinicianManager.listClinicians();
        if (clinicians.isEmpty()) {
            System.out.println("No clinicians available.");
            return null;
        }

        System.out.println("Available Clinicians:");
        for (Clinician clinician : clinicians) {
            System.out.println("ID: " + clinician.getClinicianId() + " - " + clinician);
        }

        while (true) {
        	System.out.println("Enter the Clinician ID:");
            int id = Integer.parseInt(reader.readLine());
            
            for (Clinician clinician : clinicians) {
                if (clinician.getClinicianId() == id) {
                    return clinician;
                }
            }
            System.out.println("Invalid Clinician ID. Please try again.");
        }
    }



    private Patient choosePatient() throws IOException {
        List<Patient> patients = patientManager.listPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients available.");
            return null;
        }

        System.out.println("Available Patients:");
        for (Patient patient : patients) {
            System.out.println("ID: " + patient.getId() + " - " + patient);
        }

        while (true) {
        	System.out.println("Enter the Patient ID:");
            int id = Integer.parseInt(reader.readLine());
            
            for (Patient patient : patients) {
                if (patient.getId() == id) {
                    return patient;
                }
            }
            System.out.println("Invalid Patient ID. Please try again.");
        }
    }
}

