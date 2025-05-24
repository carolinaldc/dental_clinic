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
import dentalClinicIFaces.AppointmentManager;
import dentalClinicIFaces.TreatmentManager;
import dentalClinicIFaces.UserManager;
import dentalClinicIFaces.XMLManager;
import dentalClinicJDBC.JDBCManager;
import dentalClinicJDBC.JDBCClinicianManager;
import dentalClinicJDBC.JDBCPatientManager;
import dentalClinicJDBC.JDBCAppointmentManager;
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
	private static MaterialUI materialUI;
	private static SupplierUI supplierUI;

	
	private static JDBCManager jdbcmanager;
	private static PatientManager patientManager;
	private static ClinicianManager clinicianManager;
	//private static AppointmentManager patientsTreatmentManager;
	//private static TreatmentManager treatmentManager;
	private static UserManager usermanager;
	private static BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
	//private static XMLManager xmlmanager;


	public static void main(String[] args) {
		jdbcmanager = new JDBCManager();
		patientManager = new JDBCPatientManager(jdbcmanager);
		usermanager = new JPAUserManager();
		//xmlmanager = new XMLManagerImpl();
		clinicianManager = new JDBCClinicianManager(jdbcmanager);
		//patientsTreatmentManager = new JDBCAppointmentManager(jdbcmanager);
		//treatmentManager = new JDBCTreatmentManager(jdbcmanager);
		
		patiententUI = new PatientUI(patientManager);
		clinicianUI = new ClinicianUI(clinicianManager);
		
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
			if (user == null) {
	            System.out.println("There is no account with that username or password\n");
	            return;
	        }else {
	        	if (user!=null & user.getRole().getDescription().equals("Patient")){
					patientMenu(user.getEmail(),user.getRole());
				} else if (user!=null & user.getRole().getDescription().equals("Clinician")){
					clinicianMenu(user.getEmail(), user.getRole());
				} else if (user!=null & user.getRole().getDescription().equals("Supplier")){
					supplierMenu(user.getEmail(), user.getRole());
				}
				System.out.println("Login Successful!");
	        }
			

			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void registerPage() {
	    System.out.println("Register as:");
	    System.out.println("1. Patient");
	    System.out.println("2. Clinician");
	    System.out.println("3. Supplier");

	    try {
	        String input = reader.readLine();
	        int userType;

	        try {
	            userType = Integer.parseInt(input);
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter 1 for Patient, 2 for Clinician or 3 for Supplier");
	            registerPage(); 
	            return;
	        }

	        System.out.println("Introduce mail:");
	        String email = reader.readLine();
	        System.out.println("Introduce password:");
	        String password = reader.readLine();

	        try {
	        	Role role = usermanager.getRole(userType);

	        	boolean success = createUser(email, password, role);
	        	if (!success) {
	        	    System.out.println("Email already registered. Please try again.");
	        	    registerPage(); // retry registration
	        	    return;
	        	}

	        	if (userType == 1) {
	        		patiententUI.addPatient(email);
	        		patientMenu(email,role);
	        		//patientMenu(email, role.getDescription());
	        	} else if(userType == 2){
	        		clinicianUI.addClinician(email);
	        		clinicianMenu(email, role);
	        	} else if(userType == 3) {
	        		//supplierUI.addSupplier();
	        		supplierMenu(email, role);
	        	}
	        }catch(Exception e) {
	        	System.out.println("Error during registration.");
	        	e.printStackTrace();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void patientMenu(String email, Role role) {
		System.out.println("Choose an option:");
		System.out.println("1. Profile");
		System.out.println("2. Appointments");
		System.out.println("3. Log Out");
		
		try {
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
				case 1:
					profileMenu(role);
					break;
				case 2:
					appointmentMenu();
					break;
				case 3:
					main(null);
					break;
				default:
					System.out.println("Invalid choice");
					patientMenu(email, role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void clinicianMenu(String email, Role role) {
		System.out.println("Choose an option:");
		System.out.println("1. Profile");
		System.out.println("2. Appointments");
		System.out.println("3. Treatments");
		System.out.println("4. Log Out");
		
		try {
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
				case 1:
					profileMenu(role);
					break;
				case 2:
					appointmentMenu();
					break;
				case 3:
					treatmentMenu();
					break;
				case 4:
					main(null);
					break;
				default:
					System.out.println("Invalid choice");
					patientMenu(email, role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void supplierMenu(String email, Role role) {
		System.out.println("Choose an option:");
		System.out.println("1. Profile");
		System.out.println("2. Materials");
		System.out.println("3. Log Out");
		
		try {
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
				case 1:
					profileMenu(role);
					break;
				case 2:
					materialMenu();
					break;
				case 3:
					main(null);
					break;
				default:
					System.out.println("Invalid choice");
					patientMenu(email, role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void profileMenu(Role role) { //clinician, patient, supplier
	    System.out.println("Choose an option:");
	    System.out.println("1. Edit Profile");
	    System.out.println("2. Delete Profile");

	    try {
	        int choice = Integer.parseInt(reader.readLine());
	        if ("Patient".equalsIgnoreCase(role.getDescription())) {
	            if (choice == 1) {
	            	patiententUI.modifyPatient();
	            }
	            else if (choice == 2) {
	            	patiententUI.deletePatient();
	            }
	            else {
	            	profileMenu(role);
	            }
	        } else if ("Clinician".equalsIgnoreCase(role.getDescription())) {
	            if (choice == 1) {
	            	clinicianUI.modifyClinician();
	            }
	            else if (choice == 2) {
	            	clinicianUI.deleteClinician();
	            }
	        }else if ("Supplier".equalsIgnoreCase(role.getDescription())) {
	            if (choice == 1) {
	            	//supplierUI.modifySupplier();
	            }
	            else if (choice == 2) {
	            	supplierUI.deleteSupplier();
	            }
	        }else {
	        	System.out.println("Invalid option\n");
	        	profileMenu(role);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


    public static void appointmentMenu() { //Clinicia, Patient
    	System.out.println("Choose an option:");
        System.out.println("1. Book a new Appointment");
        System.out.println("2. Modify an Appointment");
        System.out.println("3. Cancel an Appointment");
        System.out.println("4. Show List of Appointments");

        int choice;
        try {
        	choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                	appointmentUI.addAppointment();
                    break;
                case 2:
                	//appointmentUI.modifyAppointment();
                    break;
                case 3:
                	appointmentUI.deleteAppointment();
                    break;
                case 4:
                	appointmentUI.viewAppointmentsList();
                    break;
                default:
                    System.out.println("Invalid choice");
                    appointmentMenu();
            }
		}catch(Exception e){
			e.printStackTrace();
		}
    }
   

    public static void treatmentMenu() { //clinician
        System.out.println("Choose an option:");
        System.out.println("1. Create a new Treatment");
        System.out.println("2. Remove a Treatment");
        System.out.println("3. Modify a Treatment");
        System.out.println("4. Show List of Treatments");

        int choice;
        try {
        	choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                	treatmentUI.addTreatment();
                    break;
                case 2:
                	treatmentUI.deleteTreatment();
                    break;
                case 3:
                	//treatmentUI.modifyTreatment();
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
    
    
    public static void materialMenu() { //supplier
        System.out.println("Choose an option:");
        System.out.println("1. Add new Material");
        System.out.println("2. Modify a Material");
        System.out.println("3. Remove a Material");
        System.out.println("4. Show List of Materials");

        int choice;
        try {
        	choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                	materialUI.addMaterial();
                    break;
                case 2:
                	materialUI.modifyMaterial();
                    break;
                case 3:
                	materialUI.deleteMaterial();
                    break;
                case 4:
                	materialUI.viewMaterialsList();
                    break;
                default:
                    System.out.println("Invalid choice");
                    treatmentMenu();
            }
		}catch(Exception e){
			e.printStackTrace();
		}
    }
    /*
    private static void createUser(String email, String password, Role role) throws Exception {
        if (email == null || password == null || role == null) {
            throw new IllegalArgumentException("Email, password, and role cant not be null");
        }

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] pw = md.digest();

        usermanager.newUser(new User(email, pw, role));
    }*/
    
    private static boolean createUser(String email, String password, Role role) throws Exception {
    if (email == null || password == null || role == null) {
        throw new IllegalArgumentException("Email, password, and role cannot be null");
    }

    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(password.getBytes());
    byte[] pw = md.digest();

    
    User newUser = new User(email, pw, role);
    return usermanager.newUser(newUser); 
}

}