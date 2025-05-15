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
import dentalClinicIFaces.UserManager;
import dentalClinicIFaces.XMLManager;
import dentalClinicJDBC.JDBCManager;
import dentalClinicJDBC.JDBCClinicianManager;
import dentalClinicJDBC.JDBCPatientManager;
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
	
	private static JDBCManager jdbcmanager;
	private static PatientManager patientManager;
	private static ClinicianManager clinicianManager;
	private static UserManager usermanager;
	private static BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
	private static XMLManager xmlmanager;


	public static void main(String[] args) {
		jdbcmanager = new JDBCManager();
		patientManager = new JDBCPatientManager(jdbcmanager);
		usermanager = new JPAUserManager();
		xmlmanager = new XMLManagerImpl();
		clinicianManager = new JDBCClinicianManager(jdbcmanager);
		
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

        int userType;
		try {
			userType = Integer.parseInt(reader.readLine());
			
			if (userType == 1) {
				addNewUser(userType, null, null, null, null, null, null, null, null);
	            patientMenu(null);
	        } else if (userType == 2) {
	        	addNewUser(userType, null, null, null, null, null, null, null, null);
	            clinicianMenu(null);
	        } else {
	            System.out.println("Invalid choice");
	            registerPage();//le hace repetir
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    public static void patientMenu(String email) {
    	
    	patientManager.getPatient(email);
    	
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
                //logOut();
                break;
            default:
                System.out.println("Invalid choice");
                //patientMenu();
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
                //editProfile();
	        } else if (choice == 2) {
                //deleteProfile();
	        } else {
	            System.out.println("Invalid choice");
                patientProfileMenu();
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    public static void clinicianMenu(String email) {
        
    	clinicianManager.getClinician(email);
    	
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
                //editProfile();
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
                //logOut();
                break;
            default:
                System.out.println("Invalid choice");
                clinicianMenu(email);
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
                //viewAllPatientsList();
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
		
		//TODO: tengo que crear un roleID para que tenga sentido esto???
		if (userType == 1) {
			try {
				Role clinicianRole = usermanager.getRole(2);
	            usermanager.newUser(new User(email, pw, clinicianRole));
	            clinicianManager.addClinician(new Clinician(name, surname, speciality, email, phone));
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (userType == 2) {
			try {
				Role patientRole = usermanager.getRole(1);
	            usermanager.newUser(new User(email, pw, patientRole));
	            patientManager.addPatient(new Patient(name, surname, dob, phone, email, credit_card));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			 throw new IllegalArgumentException("Invalid user type");
		}
	}

}
