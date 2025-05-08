package dentalClinicUI;

import java.io.BufferedReader;
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
import dentalClinicJDBC.JDBCManager;
import dentalClinicJDBC.JDBCClinicianManager;
import dentalClinicJDBC.JDBCPatientManager;
import dentalClinicJDBC.JDBCPatientManager;
import dentalClinicJPA.JPAUserManager;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.Role;
import dentalClinicPOJOS.User;


public class menu {


	private static JDBCManager jdbcManager;
    private JDBCClinicianManager clinicianManager;
    private JDBCPatientManager patientManager;
    private static UserManager userManager;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	private static JDBCManager jdbcmanager;
	private static PatientManager patientManager;
	private static ClinicianManager clinicianManager;
	private static UserManager usermanager;
	private static BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));


	public static void main(String[] args) {

		// TODO Auto-generated method stub
		System.out.println("Hello world");
		
		jdbcManager = new JDBCManager();
		patientManager = new JDBCPatientManager(jdbcManager);
        clinicianManager = new JDBCClinicianManager(jdbcManager);
		
        
        try {
            do {
                System.out.println("\n=== Dental Clinic ===");
                System.out.println("1. Login");
                System.out.println("2. Sign-up");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
	}

	
		jdbcmanager = new JDBCManager();
		patientManager = new JDBCPatientManager(jdbcmanager);
		usermanager = new JPAUserManager();
		
		int choice=0;
		try {
			do {
				System.out.println("Welcome to our Dental Clinic");
				System.out.println("Choose an option:");
				System.out.println("1. Login");
				System.out.println("2. Register");
				System.out.println("2. Exit");
				
			
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
			
			if(user != null & user.getRole().getDescription().equals("Patient")) {
				System.out.println("Login Successful!");
				patientMenu(user.getEmail());
			}else if(user != null & user.getRole().getDescription().equals("Clinician")) {
				System.out.println("Login Successful!");
				clinicianMenu(user.getEmail());
			}else if(user == null) 
				System.out.println("There is no user with these credentials");
			
			
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
	            //enterPatientData();
	            //patientMenu();
	        } else if (userType == 2) {
	            //enterClinicianData();
	            //clinicianMenu();
	        } else {
	            System.out.println("Invalid choice");
	            registerPage();//le hace repetir
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    public static void patientMenu(String email) {
        
    	//lo que puso katerina
    	patientManager.getPatient(email);
    	//
    	
    	System.out.println("Choose an option:");
        System.out.println("1. Profile");
        System.out.println("2. Appointments");
        System.out.println("3. Treatments");
        System.out.println("4. Log Out");

        int choice;
        try {
        	choice = Integer.parseInt(reader.readLine());
			
        	switch (choice) {
            case 1:
                patientProfileMenu();
                break;
            case 2:
                patientAppointmentsMenu();
                break;
            case 3:
                //TODO: este no sabia como hacerlo
                //patientMenu();
                break;
            case 4:
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

    public static void patientAppointmentsMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Book Appointment");
        System.out.println("2. Modify Appointment");
        System.out.println("3. Cancel Appointment");

        int choice;
        try {
        	choice = Integer.parseInt(reader.readLine());

        	switch (choice) {
            case 1:
                //bookAppointment();
                break;
            case 2:
                //choseAppointment();
                //modifyAppointment();
                break;
            case 3:
                //choseAppointment();
                //cancelAppointment();
                break;
            default:
                System.out.println("Invalid choice");
                patientAppointmentsMenu();
        	}
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    public static void clinicianMenu(String email) {
        
    	//lo que puso katerina
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
                clinicianAppointmentsMenu();
                break;
            case 3:
                clinicianTreatmentsMenu();
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

    public static void clinicianAppointmentsMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. All My Appointments");
        System.out.println("2. Today's Appointments");

        int choice;
        try {
        	choice = Integer.parseInt(reader.readLine());

            if (choice == 1) {
                //viewAllAppointmentsList();
                clinicianAppointmentsOptions();
            } else if (choice == 2) {
                //viewTodaysAppointmentsList();
            } else {
                System.out.println("Invalid choice");
                clinicianAppointmentsMenu();
            }
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    public static void clinicianAppointmentsOptions() {
        System.out.println("Choose an option:");
        System.out.println("1. Book");
        System.out.println("2. Modify");
        System.out.println("3. Cancel");
        System.out.println("4. Show Details");

        int choice;
        try {
        	choice = Integer.parseInt(reader.readLine());

            switch (choice) {
            case 1:
                //bookAppointment();
                break;
            case 2:
                //choseAppointment();
                //modifyAppointment();
                break;
            case 3:
                //choseAppointment();
                //cancelAppointment();
                break;
            case 4:
                //patientDetails();
                //treatmentDetails();
                break;
            default:
                System.out.println("Invalid choice");
                clinicianAppointmentsOptions();
            }
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    public static void clinicianTreatmentsMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Book Treatment");
        System.out.println("2. Modify Treatment");
        System.out.println("3. Cancel Treatment");
        System.out.println("4. Show List of Treatments");


        int choice;
        try {
        	choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                    //addTreatment();
                    break;
                case 2:
                    //viewTreatmentsList();
                    //chooseTreatment();
                    //modifyTreatment();
                    break;
                case 3:
                    //viewTreatmentsList();
                    //chooseTreatment();
                    //deleteTreatment();
                    break;
                case 4:
                    //viewTreatmentsList();
                    break;
                default:
                    System.out.println("Invalid choice");
                    clinicianTreatmentsMenu();
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
    
  //TODO no hemos hecho el addPatient
    
    private static void addNewUser() {
    	
    	try {
    		System.out.println("Introduce email:");
    		String mail = reader.readLine();
    		System.out.println("Introduce password:");
    		String password = reader.readLine();
    		MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] digest = md.digest();
			
			System.out.println("Which is your role?:");
			List<Role> roles = usermanager.getRoles();
			System.out.println(roles.toString());
			Integer role = Integer.parseInt(reader.readLine());				
			Role rol = usermanager.getRole(role);
			
			User user = new User(mail,digest,rol);
			usermanager.newUser(user);
    		
    	}catch(Exception e)
		{
			e.printStackTrace();
		}
    	
    }
}
