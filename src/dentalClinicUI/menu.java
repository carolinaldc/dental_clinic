package dentalClinicUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


import dentalClinicIFaces.ClinicianManager;
import dentalClinicIFaces.PatientManager;
import dentalClinicIFaces.SupplierManager;
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
import dentalClinicPOJOS.Supplier;
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
	private static AppointmentManager appointmentManager;
	private static SupplierManager supplierManager;

	private static TreatmentManager treatmentManager;
	private static UserManager usermanager;
	private static BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
	//private static XMLManager xmlmanager;


	private static User currentUser;

	
	public static void main(String[] args) {
		
		jdbcmanager = new JDBCManager();
		usermanager = new JPAUserManager();
		patientManager = new JDBCPatientManager(jdbcmanager);
		clinicianManager = new JDBCClinicianManager(jdbcmanager);
		//supplierManager = new JDBCSupplierManager(jdbcmanager);

		appointmentManager = new JDBCAppointmentManager(jdbcmanager);
		treatmentManager = new JDBCTreatmentManager(jdbcmanager);
		//xmlmanager = new XMLManagerImpl();

		
		patiententUI = new PatientUI(patientManager);
		clinicianUI = new ClinicianUI(clinicianManager);
		treatmentUI = new TreatmentUI(treatmentManager);
		appointmentUI = new AppointmentUI(appointmentManager);
		//supplierUI = new SupplierUI(supplierManager);
		//appointmentUI = new AppointmentUI(appointmentManager, patiententUI, clinicianUI, treatmentUI);

		
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
			} else {
			    if (user.getRole().getDescription().equals("Patient")) {
			        Patient patient = patientManager.getPatientByEmail(user.getEmail()); 
			        patiententUI.setCurrentPatient(patient);                        
			        patientMenu(user.getEmail(), user.getRole());
			    } else if (user.getRole().getDescription().equals("Clinician")) {
			        Clinician clinician = clinicianManager.getClinicianByEmail(user.getEmail()); 
			        clinicianUI.setCurrentClinician(clinician);
			        clinicianMenu(user.getEmail(), user.getRole());
			    } else if (user.getRole().getDescription().equals("Supplier")) {
			        //Supplier supplier = supplierManager.getSuppliertByEmail(user.getEmail()); 
			        //supplierUI.setCurrentSupplier(supplier);
			        supplierMenu(user.getEmail(), user.getRole());
			    }
			    System.out.println("Login Successful!");
			}

			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void registerPage() {
	    System.out.println("\nRegister as:");
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
	        	    patiententUI.addPatient(email);  // add patient first
	        	    Patient patient = patientManager.getPatientByEmail(email); // now fetch the newly added patient
	        	    if (patient != null) {
	        	        patiententUI.setCurrentPatient(patient); // set current patient properly
	        	        patientMenu(email, role);
	        	    } else {
	        	        System.out.println("Error: Could not find newly added patient.");
	        	    }
	        	}else if(userType == 2){
	        		clinicianUI.addClinician(email);
	        		clinicianMenu(email, role);
	        	} else if(userType == 3) {
	        		supplierUI.addSupplier();
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
		System.out.println("\nPatient menu");
		System.out.println("Choose an option:");
		System.out.println("1. Profile");
		System.out.println("2. Appointments");
		System.out.println("3. Log Out");
		
		try {
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
				case 1:
					profileMenu(email, role);
					break;
				case 2:
					//appointmentMenu(email, role);
					break;
				case 3:
	                currentUser = null;
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
		System.out.println("\nClinician menu");
		System.out.println("Choose an option:");
		System.out.println("1. Profile");
		System.out.println("2. Appointments");
		System.out.println("3. Treatments");
		System.out.println("4. Log Out");
		
		try {
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
				case 1:
					profileMenu(email, role);
					break;
				case 2:
					//appointmentMenu(email, role);
					break;
				case 3:
					treatmentMenu(email, role);
					break;
				case 4:
					currentUser =null;
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
		System.out.println("\nSupplier menu");
		System.out.println("Choose an option:");
		System.out.println("1. Profile");
		System.out.println("2. Materials");
		System.out.println("3. Log Out");
		
		try {
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
				case 1:
					profileMenu(email, role);
					break;
				case 2:
					materialMenu(email, role);
					break;
				case 3:
					currentUser =null;
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
	
	
	public static void profileMenu(String email, Role role) { //clinician, patient, supplier
		int choice = 0;
			do {
				System.out.println("\nProfile menu");
			    System.out.println("Choose an option:");
			    System.out.println("1. Edit Profile");
			    System.out.println("2. Delete Profile");
			    System.out.println("3. Go back");
	
			    try {
		            String input = reader.readLine();
		            choice = Integer.parseInt(input);
		        } catch (Exception e) {
		            System.out.println("Invalid input. Please enter a number.");
		            continue; // retry input
		        }
			    
		        if ("Patient".equalsIgnoreCase(role.getDescription())) {
		            if (choice == 1) {
		            	patiententUI.modifyPatient();
		            }
		            else if (choice == 2) {
		            	patiententUI.deletePatient();
		                currentUser = null; 
		            }
		        } else if ("Clinician".equalsIgnoreCase(role.getDescription())) {
		            if (choice == 1) {
		            	clinicianUI.modifyClinician();
		            }
		            else if (choice == 2) {
		            	clinicianUI.deleteClinician();
		                currentUser = null; 
		            }
		        }else if ("Supplier".equalsIgnoreCase(role.getDescription())) {
		            if (choice == 1) {
		            	//supplierUI.modifySupplier();
		            }
		            else if (choice == 2) {
		            	supplierUI.deleteSupplier();
		                currentUser = null; 
		            }
		        }
		        
			} while (choice != 3);
	        if ("Patient".equalsIgnoreCase(role.getDescription())) {
	        	patientMenu(email, role);
	        } else if ("Clinician".equalsIgnoreCase(role.getDescription())) {
	        	clinicianMenu(email, role);
	        } else if ("Supplier".equalsIgnoreCase(role.getDescription())) {
	        	supplierMenu(email, role);
	        }
	}

/*
    public static void appointmentMenu(String email, Role role) throws ParseException { //Clinicia, Patient
		int choice = 0;

			do {
				System.out.println("Appointment menu");
		    	System.out.println("Choose an option:");
		        System.out.println("1. Book a new Appointment");
		        System.out.println("2. Modify an Appointment");
		        System.out.println("3. Cancel an Appointment");
		        System.out.println("4. Show List of Appointments");
			    System.out.println("5. Go back");
	    
			    try {
		            String input = reader.readLine();
		            choice = Integer.parseInt(input);
		        } catch (Exception e) {
		            System.out.println("Invalid input. Please enter a number.");
		            continue; // retry input
		        }
			    
	            switch (choice) {
	                case 1:
	                	appointmentUI.addAppointment(email, role);
	                    break;
	                if (appointmentUI.getListOfAppointments(email, role).isEmpty()) {
	                        System.out.println("There aren't any appointments to show.");
	                } else {
	                	case 2:
	                	//appointmentUI.modifyAppointment();
	                	if ("Patient".equalsIgnoreCase(role.getDescription())) {
		                	//appointmentUI.addPatientAppointment();
	        	        } else if ("Clinician".equalsIgnoreCase(role.getDescription())) {
		                	//appointmentUI.addClinicianAppointment();
	        	        }
	                	//appointmentUI.modifyAppointment();
	                    break;
	                case 3:
	                	if ("Patient".equalsIgnoreCase(role.getDescription())) {
		                	//appointmentUI.deleteAppointment();
	        	        } else if ("Clinician".equalsIgnoreCase(role.getDescription())) {
		                	//appointmentUI.deleteAppointment();
	        	        }
	                	appointmentUI.deleteAppointment(); //idk if i should do one for the specific user
	                    break;
	                case 4:
	                	if ("Patient".equalsIgnoreCase(role.getDescription())) {
		                	//appointmentUI.viewPatientAppointmentsList();
		                	appointmentUI.viewAppointmentsList();
	        	        } else if ("Clinician".equalsIgnoreCase(role.getDescription())) {
		                	//appointmentUI.viewClinicianAppointmentsList();
	        	        }
	                	appointmentUI.viewAppointmentsList(); //idk if i should do one for the specific user
	                    break;
	                }
	                default:
	                    System.out.println("Invalid choice");
	                    appointmentMenu(email, role);
	            }
	    	}while(choice !=5);
        	if ("Patient".equalsIgnoreCase(role.getDescription())) {
            	patientMenu(email, role);
	        } else if ("Clinician".equalsIgnoreCase(role.getDescription())) {
	        	clinicianMenu(email, role);
	        }
    }
   
    */

    public static void treatmentMenu(String email, Role role) { //clinician
		int choice = 0;

			do {
				System.out.println("Treatment menu");
		        System.out.println("Choose an option:");
		        System.out.println("1. Create a new Treatment");
		        System.out.println("2. Remove a Treatment");
		        System.out.println("3. Modify a Treatment");
		        System.out.println("4. Show List of Treatments");
			    System.out.println("5. Go back");

			    try {
		            String input = reader.readLine();
		            choice = Integer.parseInt(input);
		        } catch (Exception e) {
		            System.out.println("Invalid input. Please enter a number.");
		            continue; // retry input
		        }
			    
        		switch (choice) {
	                case 1:
	                	treatmentUI.addTreatment();
	                    break;
	                case 2:
	                	treatmentUI.deleteTreatment();
	                    break;
	                case 3:
	                	treatmentUI.modifyTreatment();
	                    break;
	                case 4:
	                	treatmentUI.viewTreatmentsList();
	                    break;
	                default:
	                    System.out.println("Invalid choice");
	                    treatmentMenu(email, role);
	            }
        	}while(choice !=5);
        	clinicianMenu(email, role);
    }
    
    
    public static void materialMenu(String email, Role role) { //supplier
		int choice = 0;
		do {
			System.out.println("Material menu");
		    System.out.println("Choose an option:");
		    System.out.println("1. Add new Material");
		    System.out.println("2. Modify a Material");
		    System.out.println("3. Remove a Material");
	        System.out.println("4. Show List of Materials");
		    System.out.println("5. Go back");

		    try {
	            String input = reader.readLine();
	            choice = Integer.parseInt(input);
	        } catch (Exception e) {
	            System.out.println("Invalid input. Please enter a number.");
	            continue; // retry input
	        }
			    
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
	                materialMenu(email, role);
	        }
	    }while(choice !=5);
		supplierMenu(email, role);
    }

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