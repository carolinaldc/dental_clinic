package dentalClinicUI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import dentalClinicJDBC.JDBCManager;
import dentalClinicJDBC.JDBCPatientManager;
import dentalClinicPOJOS.Patient;


public class menu {
	
	private static JDBCManager jdbcmanager;
	private static JDBCPatientManager patientManager;
	private static BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));

	public static void main(String[] args) {
	
		jdbcmanager = new JDBCManager();
		int choice=0;
		try {
			do {
				System.out.println("Welcome to our Dental Clinic");
				System.out.println("Choose an option:");
				System.out.println("1. Login");
				System.out.println("2. Register");
			
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
						break;
				}
			}while(choice!=0);
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    public static void loginPage() {
        System.out.println("Login as:");
        System.out.println("1. Patient");
        System.out.println("2. Clinician");

        int userType;
		try {
			userType = Integer.parseInt(reader.readLine());
			
			if (userType == 1) {
	            patientMenu();
	        } else if (userType == 2) {
	            clinicianMenu();
	        } else {
	            System.out.println("Invalid choice");
	            loginPage(); //le hace repetir
	        }
		}catch(Exception e){
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
	            patientMenu();
	        } else if (userType == 2) {
	            //enterClinicianData();
	            clinicianMenu();
	        } else {
	            System.out.println("Invalid choice");
	            registerPage();//le hace repetir
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    public static void patientMenu() {
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
                patientMenu();
                break;
            case 4:
                //logOut();
                break;
            default:
                System.out.println("Invalid choice");
                patientMenu();
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

    public static void clinicianMenu() {
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
                clinicianMenu();
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
}