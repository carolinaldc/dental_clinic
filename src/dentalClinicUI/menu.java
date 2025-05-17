package dentalClinicUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


import dentalClinicIFaces.ClinicianManager;
import dentalClinicIFaces.PatientManager;
import dentalClinicIFaces.PatientsTreatmentManager;
import dentalClinicIFaces.TreatmentManager;
import dentalClinicIFaces.UserManager;
import dentalClinicIFaces.XMLManager;
import dentalClinicJDBC.JDBCManager;
import dentalClinicJDBC.JDBCClinicianManager;
import dentalClinicJDBC.JDBCPatientManager;
import dentalClinicJDBC.JDBCPatientTreatmentManager;
import dentalClinicJDBC.JDBCTreatmentManager;
import dentalClinicJDBC.JDBCPatientManager;
import dentalClinicJPA.JPAUserManager;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.Role;
import dentalClinicPOJOS.User;
import dentalClinicXML.XMLManagerImpl;


public class menu {
	private static TreatmentUI treatmentUI;
	private static AppointmentUI appointmentUI;
	private static PatientUI patiententUI;
	private static ClinicianUI clinicianUI;
	
	private static JDBCManager jdbcmanager;
	private static PatientManager patientManager;
	private static ClinicianManager clinicianManager;
	private static PatientsTreatmentManager patientsTreatmentManager;
	private static TreatmentManager treatmentManager;
	private static UserManager usermanager;
	private static BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
	private static XMLManager xmlmanager;


	public static void main(String[] args) {
		jdbcmanager = new JDBCManager();
		patientManager = new JDBCPatientManager(jdbcmanager);
		usermanager = new JPAUserManager();
		xmlmanager = new XMLManagerImpl();
		clinicianManager = new JDBCClinicianManager(jdbcmanager);
		patientsTreatmentManager = new JDBCPatientTreatmentManager(jdbcmanager);
		treatmentManager = new JDBCTreatmentManager(jdbcmanager);
		
		patiententUI = new PatientUI(patientManager);
		clinicianUI = new ClinicianUI(clinicianManager);
		//appointmentUI = new AppointmentUI(patientManager, clinicianManager, patientsTreatmentManager, treatmentManager); 

		
		int choice=0;
		try {
			do {
				System.out.println("Welcome to our Dental Clinic");
				System.out.println("Choose an option:");
				System.out.println("1. Login");
				System.out.println("2. Register");
				System.out.println("0. Exit");
				
			
				choice = Integer.parseInt(reader.readLine());

				switch (choice) {
					case 1:
						loginPage();
						break;
					case 2:
						registerPage();
						break;
					case 0:				
						jdbcmanager.closeConnection();
						usermanager.disconnect();
						System.exit(0); 
						break;
				}
			}while(choice!=0);
		}catch(Exception e){
			e.printStackTrace();
		}
    }

	private static void loginPage() {
		try {
			System.out.println("Introduce mail:");
			String mail = reader.readLine();
			System.out.println("Introduce password");
			String password = reader.readLine();
			
			User user = usermanager.checkPassword(mail, password);
			
			if(user != null && user.getRole().getDescription().equals("Patient")) {
				System.out.println("Login Successful!");
				patientMenu(user.getEmail());
			}else if(user != null && user.getRole().getDescription().equals("Clinician")) {
				System.out.println("Login Successful!");
				clinicianMenu(user.getEmail());
			}else { 
				System.out.println("There is no user with these credentials");
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void registerPage() {
	    System.out.println("Register as:");
	    System.out.println("1. Patient");
	    System.out.println("2. Clinician");

	    try {
	        String input = reader.readLine();
	        int userType;

	        try {
	            userType = Integer.parseInt(input);
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter 1 for Patient or 2 for Clinician");
	            registerPage(); // Retry
	            return;
	        }

	        System.out.println("Introduce mail:");
	        String email = reader.readLine();
	        System.out.println("Introduce password:");
	        String password = reader.readLine();

	        try {
	        	Role role = usermanager.getRole(userType);
	        	createUser(email, password, role);

	        	if (userType == 1) {
	        		patiententUI.addPatient(email);
	        		patientMenu(email);
	        	} else {
	        		clinicianUI.addClinician();
	        		clinicianMenu(email);
	        	}
	        }catch(Exception e) {
	        	System.out.println("Error during registration.");
	        	e.printStackTrace();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


    public static void patientMenu(String email) {

    	Patient patient = patientManager.getPatient(email);
    	if (patient == null) {
    	    System.out.println("No patient found with email: " + email);
    	    return;
    	}
    	patiententUI.setCurrentPatient(patient);

    	
    	xmlmanager.patient2xml(1);
    	File file = new File("./xmls/external_clinician.xml");
    	Clinician c = xmlmanager.xml2Clinician(file);
 		
    	clinicianManager.addClinician(c);	
    	
    	System.out.println("Choose an option:");
        System.out.println("1. Profile");
        System.out.println("2. Appointments");
        System.out.println("3. Log Out");

        int choice;
        try {
        	choice = Integer.parseInt(reader.readLine());
			
        	switch (choice) {
            case 1:
                patientProfileMenu();
                break;
            case 2:
            	appointmentMenu();
                break;
            case 3:
                main(null); //log out??
                break;
            default:
                System.out.println("Invalid choice");
                patientMenu(email);
        	}
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    public static void patientProfileMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Edit Profile");
        System.out.println("2. Delete Profile");

        int choice;
        try {
        	choice = Integer.parseInt(reader.readLine());

        	if (choice == 1) {
        		patiententUI.modifyPatient();
	        } else if (choice == 2) {
	        	patiententUI.deletePatient();
	        } else {
	            System.out.println("Invalid choice");
                patientProfileMenu();
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
    }
  

    public static void clinicianMenu(String email) {
        
    	clinicianManager.getClinicianByEmail(email);
    	
        System.out.println("Choose an option:");
        System.out.println("1. Profile");
        System.out.println("2. Appointments");
        System.out.println("3. Treatments");
        System.out.println("4. Patients");
        System.out.println("5. Log Out");

        int choice;
        try {
        	choice = Integer.parseInt(reader.readLine());

            switch (choice) {
            case 1:
            	clinicianProfileMenu();
                break;
            case 2:
                appointmentMenu();
                break;
            case 3:
            	treatmentMenu();
                break;
            case 4:
                clinicianPatientsMenu();
                break;
            case 5:
                main(null); //log out??
                break;
            default:
                System.out.println("Invalid choice");
                clinicianMenu(email);
            }
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    public static void clinicianProfileMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Edit Profile");
        System.out.println("2. Delete Profile");

        int choice;
        try {
        	choice = Integer.parseInt(reader.readLine());

        	if (choice == 1) {
        		clinicianUI.modifyClinician();
	        } else if (choice == 2) {
	        	clinicianUI.deleteClinician();
	        } else {
	            System.out.println("Invalid choice");
                patientProfileMenu();
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
    }
    
    public static void appointmentMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Book an Appointment");
        System.out.println("2. Modify an Appointment");
        System.out.println("3. Cancel an Appointment");
        System.out.println("4. Show List of Appointment");

        int choice;
        try {
        	choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                	appointmentUI.bookAppointment();
                    break;
                case 2:
                	appointmentUI.modifyAppointment();
                    break;
                case 3:
                	appointmentUI.cancelAppointment();
                    break;
                case 4:
                	appointmentUI.viewAppointmentsList();
                    break;    
                default:
                    System.out.println("Invalid choice");
                    treatmentMenu();
            }
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    public static void treatmentMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Create a new Treatment");
        System.out.println("2. Modify a Treatment");
        System.out.println("3. Delete a Treatment");
        System.out.println("4. Show List of possible Treatments");

        int choice;
        try {
        	choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                	treatmentUI.addTreatment();
                    break;
                case 2:
                	treatmentUI.modifyTreatment();
                    break;
                case 3:
                	treatmentUI.deleteTreatment();
                    break;
                case 4:
                	treatmentUI.viewTreatmentsList();
                    break;
                default:
                    System.out.println("Invalid choice");
                    treatmentMenu();
            }
		}catch(Exception e){
			e.printStackTrace();
		}
    }
    
    public static void clinicianPatientsMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Show All My Patients");
        System.out.println("2. Show Today's Patients");
        System.out.println("3. Show Tomorrow's Patients");

        int choice;
        try {
        	choice = Integer.parseInt(reader.readLine());

            switch (choice) {
            case 1:
                //viewPatientsList();
                break;
            case 2:
                //viewTodaysPatientsList();
                break;
            case 3:
                //viewTomorrowsPatientsList();
                break;
            default:
                System.out.println("Invalid choice");
                clinicianPatientsMenu();
            }
		}catch(Exception e){
			e.printStackTrace();
		} 
    }
        
    
    private static void addNewUser(int userType, String name, String surname, String speciality, Date dob, Integer phone, String email, Integer credit_card,
			String password) throws java.sql.SQLException, Exception {
    	
    	MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] pw = md.digest();
		
		if (userType == 2) {
			try {
				Role clinicianRole = usermanager.getRole(2);
	            usermanager.newUser(new User(email, pw, clinicianRole));
	            clinicianManager.addClinician(new Clinician(name, surname, speciality, phone));
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (userType == 1) {
			try {
				Role patientRole = usermanager.getRole(1);
	            usermanager.newUser(new User(email, pw, patientRole));
	            patientManager.addPatient(new Patient(name, surname, dob, phone, credit_card));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			 throw new IllegalArgumentException("Invalid user type");
		}
	}
    
    private static void createUser(String email, String password, Role role) throws Exception {
        if (email == null || password == null || role == null) {
            throw new IllegalArgumentException("Email, password, and role cant not be null");
        }

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] pw = md.digest();

        usermanager.newUser(new User(email, pw, role));
    }


}
