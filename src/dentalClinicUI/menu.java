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
import java.util.ArrayList;
import java.util.List;


import dentalClinicIFaces.ClinicianManager;
import dentalClinicIFaces.MaterialManager;
import dentalClinicIFaces.PatientManager;
import dentalClinicIFaces.SupplierManager;
import dentalClinicIFaces.AppointmentManager;
import dentalClinicIFaces.TreatmentManager;
import dentalClinicIFaces.UserManager;
import dentalClinicIFaces.XMLManager;
import dentalClinicJDBC.JDBCManager;
import dentalClinicJDBC.JDBCMaterialManager;
import dentalClinicJDBC.JDBCClinicianManager;
import dentalClinicJDBC.JDBCPatientManager;
import dentalClinicJDBC.JDBCSupplierManager;
import dentalClinicJDBC.JDBCAppointmentManager;
import dentalClinicJDBC.JDBCTreatmentManager;
import dentalClinicJDBC.JDBCPatientManager;
import dentalClinicJPA.JPAUserManager;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.Role;
import dentalClinicPOJOS.Supplier;
import dentalClinicPOJOS.User;
import dentalClinicXML.XMLManagerImpl;


public class menu {
    private static TreatmentUI treatmentUI;
    private static AppointmentUI appointmentUI;
    private static PatientUI patientUI;       // renamed from patiententUI
    private static ClinicianUI clinicianUI;
    private static MaterialUI materialUI;
    private static SupplierUI supplierUI;

    private static JDBCManager jdbcmanager;
    private static PatientManager patientManager;
    private static ClinicianManager clinicianManager;
    private static AppointmentManager appointmentManager;
    private static SupplierManager supplierManager;
    private static MaterialManager materialManager;
    private static JPAUserManager jpaUserManager;
    private static XMLManager xmlManager;
    
    private static TreatmentManager treatmentManager;
    private static UserManager usermanager;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static User currentUser;

    public static void main(String[] args) {
        jdbcmanager = new JDBCManager();
        usermanager = new JPAUserManager();
        
        patientManager = new JDBCPatientManager(jdbcmanager);
        clinicianManager = new JDBCClinicianManager(jdbcmanager);
        supplierManager = new JDBCSupplierManager(jdbcmanager);

        materialManager = new JDBCMaterialManager(jdbcmanager);
        treatmentManager = new JDBCTreatmentManager(jdbcmanager);
        appointmentManager = new JDBCAppointmentManager(jdbcmanager);


        patientUI = new PatientUI(patientManager);
        clinicianUI = new ClinicianUI(clinicianManager);
        supplierUI = new SupplierUI(supplierManager);

        materialUI = new MaterialUI(materialManager, reader, null); 
        treatmentUI = new TreatmentUI(treatmentManager, materialManager, materialUI, reader);
        appointmentUI = new AppointmentUI(appointmentManager, patientManager, clinicianManager, treatmentManager, reader);
        jpaUserManager = new JPAUserManager();
        xmlManager = new XMLManagerImpl();


	
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
			        patientUI.setCurrentPatient(patient);                        
			        patientMenu(user.getEmail(), user.getRole());
			    } else if (user.getRole().getDescription().equals("Clinician")) {
			        Clinician clinician = clinicianManager.getClinicianByEmail(user.getEmail()); 
			        clinicianUI.setCurrentClinician(clinician);
			        clinicianMenu(user.getEmail(), user.getRole());
			    } else if (user.getRole().getDescription().equals("Supplier")) {
			        Supplier supplier = supplierManager.getSupplierByEmail(user.getEmail()); 
			        supplierUI.setCurrentSupplier(supplier);
			        materialUI.setCurrentSupplier(supplier);
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

	        	switch (userType) {
                case 1:
                    patientUI.addPatient(email);
                    break;
                case 2:
                    clinicianUI.addClinician(email);
                    break;
                case 3:
                    supplierUI.addSupplier(email);
                    break;
                default:
                    System.out.println("Invalid user type.");
                    return;
            }
	        
	        	System.out.println("Registration successful. Please login from the main menu.");
	        	
	        	
	        }catch(Exception e) {
	        	System.out.println("Error during registration.");
	        	e.printStackTrace();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }}
	/*
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
	        		patientUI.addPatient(email); 
	        	    Patient patient = patientManager.getPatientByEmail(email);
	        	    if (patient != null) {
	        	    	patientUI.setCurrentPatient(patient);
	        	        patientMenu(email, role);
	        	    } else {
	        	        System.out.println("Error: Could not find newly added patient.");
	        	    }
	        	}else if(userType == 2){
	        		clinicianUI.addClinician(email);
	        		clinicianMenu(email, role);
	        	} else if(userType == 3) {
	        		supplierUI.addSupplier(email);
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
	*/
	
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
					appointmentMenu(email, role);
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
		System.out.println("4. Export Clinician to XML");
		System.out.println("5. Import Clinician from XML");
		System.out.println("6. Delete Clinician that does not have a user (imported through XML)");
		System.out.println("7. Log Out");
		
		try {
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
				case 1:
					profileMenu(email, role);
					break;
				case 2:
					appointmentMenu(email, role);
					break;
				case 3:
					treatmentMenu(email, role);
					break;
				case 4:
					System.out.println("Exporting clinician to XML...");
			        Clinician clinician = clinicianManager.getClinicianByEmail(email);
			        Integer clincian_id = clinician.getClinician_id();
			        xmlManager.clinician2xml(clincian_id);
			        System.out.println("Export successful.");
			        clinicianMenu(email, role);
					break;
				case 5:
					System.out.println("Importing clinician from XML...");
					
					try {
						File xmlFile = new File("./xmls/external_clinician.xml");  // Adjust path if needed
						
						if (!xmlFile.exists()) {
				            System.out.println("XML file not found.");
				            break;}
						
						Clinician importedClinician = xmlManager.xml2Clinician(xmlFile);
				        if (importedClinician == null) {
				            System.out.println("Failed to import clinician from XML.");
				            break;
				        }
				        
				     // Check if a clinician with this email already exists
				        Clinician existing = clinicianManager.getClinicianByEmail(importedClinician.getEmail());
				        if (existing != null) {
				            System.out.println("A clinician with this email already exists: " + importedClinician.getEmail());
				            System.out.println("Import cancelled to avoid duplicates.");
				        } else {
				            clinicianManager.addClinician(importedClinician);
				            System.out.println("Clinician imported successfully.");
				        }
				       
				        
					}catch (Exception e) {
				        System.out.println("Error during import:");
				        e.printStackTrace();
				    }
					clinicianMenu(email, role);
					break;
				case 6:
					manageOrphanCliniciansMenu();
					break;
				case 7:
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
	
	public static void manageOrphanCliniciansMenu() {
		try {
			
			List<Clinician> allClinicians = clinicianManager.getListOfClinicians();
			List<Clinician> orphanClinicians = new ArrayList<>();
			
			for (Clinician c : allClinicians) {
	            boolean hasUser = usermanager.getUserByEmail(c.getEmail()) != null;
	            if (!hasUser) {
	                orphanClinicians.add(c);
	                System.out.println("ID: " + c.getClinician_id() + " | Name: " + c.getName() + " | Email: " + c.getEmail());
	            }
	        }
			
			if (orphanClinicians.isEmpty()) {
	            System.out.println("No orphan clinicians found.");
	            return;
	        }
			
			System.out.println("Enter the ID of the clinician to delete, or '0' to cancel:");
	        int idToDelete = Integer.parseInt(reader.readLine());
	        
	        if (idToDelete == 0) {
	            System.out.println("Operation cancelled.");
	            return;
	        }
	        
	        Clinician cToDelete = clinicianManager.getClinicianById(idToDelete);
	        boolean hasUser = usermanager.getUserByEmail(cToDelete.getEmail()) != null;
	        
	        if (hasUser) {
	            System.out.println("Cannot delete this clinician. They are linked to a user.");
	        } else {
	            clinicianManager.deleteClinician(idToDelete);
	            System.out.println("Clinician deleted successfully.");
	        }
			
			
		}catch (Exception e) {
	        System.out.println("Error occurred: " + e.getMessage());
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
		            	patientUI.modifyPatient();
		            }
		            else if (choice == 2) {
		            	//delete user 
		            	User user = jpaUserManager.getUserByEmail(email);
		            	if (user != null) {
		            		patientUI.deletePatientByEmail();
		            		jpaUserManager.deleteUser(user);
		            		currentUser = null;
		            		System.out.println("Your profile has been deleted.");
		            		return; // Exit the menu after deletion
		            	}else {
		                    System.out.println("Error: User not found.");
		                }
		            }
		        } else if ("Clinician".equalsIgnoreCase(role.getDescription())) {
		            if (choice == 1) {
		            	clinicianUI.modifyClinician();
		            }
		            else if (choice == 2) {
		            	//delete user 
		            	User user = jpaUserManager.getUserByEmail(email);
		            	if (user != null) {
		            		clinicianUI.deleteClinicianByEmail();
		            		jpaUserManager.deleteUser(user);
			                currentUser = null; 
			                System.out.println("Your profile has been deleted.");
			                return;
		            	}
		            	
		            }
		        }else if ("Supplier".equalsIgnoreCase(role.getDescription())) {
		            if (choice == 1) {
		            	supplierUI.modifySupplier();
		            }
		            else if (choice == 2) {
		            	//delete user 
		            	User user = jpaUserManager.getUserByEmail(email);
		            	if (user != null) {
		            		supplierUI.deleteSupplierByEmail();
		            		jpaUserManager.deleteUser(user);
			                currentUser = null; 
			                System.out.println("Your profile has been deleted.");
			                return;
		            	}

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


    public static void appointmentMenu(String email, Role role) throws ParseException { //Clinicia, Patient
		int choice = 0;

			do {
				System.out.println("\nAppointment menu");
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
	                	appointmentUI.deleteAppointment(email, role);
	                    break;
	                case 4:
	                	appointmentUI.viewAppointmentsList(email, role);
	                    break;
	                case 5:
	                	break;
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
   
    

    public static void treatmentMenu(String email, Role role) { //clinician
		int choice = 0;

			do {
				System.out.println("\nTreatment menu");
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
	                case 5:
	                	//goes to the while
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
			System.out.println("\nMaterial menu");
		    System.out.println("Choose an option:");
		    System.out.println("1. Add new Material");
		    System.out.println("2. Modify a Material");
		    System.out.println("3. Remove a Material");
	        System.out.println("4. Show List of Materials");
	        System.out.println("5. Export Material to XML");
	        System.out.println("6. Import Material from XML");
		    System.out.println("7. Go back");

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
	           case 5:
	        	   System.out.println("Exporting material to XML...");
	        	   Supplier supplier = supplierManager.getSupplierByEmail(email);
	        	   Integer supplier_id = supplier.getSupplier_id();
	        	   
	        	   List<Material> materials = materialManager.getListOfSupplier_Materials(supplier_id);
	        	   
	        	   if (materials == null || materials.isEmpty()) {
	        	        System.out.println("No materials found for this supplier.");
	        	        break;
	        	    }
	        	   
	        	   System.out.println("Select a material to export by ID:");
	        	    for (Material m : materials) {
	        	        System.out.println("ID: " + m.getMaterials_id() + " | Name: " + m.getName());
	        	    }
	        	    
	        	    Integer matId;
	        	    try {
	    	            String input2 = reader.readLine();
	    	            matId = Integer.parseInt(input2);
	    	        } catch (Exception e) {
	    	            System.out.println("Invalid input. Please enter a number.");
	    	            continue; // retry input
	    	        }
	        	    
	        	   xmlManager.material2xml(matId);
	        	   System.out.println("Export successful.");
	        	   materialMenu(email, role);
	        	   break;
	        	   
	           case 6:
	        	   System.out.println("Importing material from XML...");
	        	   File xmlFile = new File("./xmls/external_material.xml");  // Adjust path if needed
	        	   if (!xmlFile.exists()) {
	                   System.out.println("File not found");
	                   break;
	               }
	        	   
	        	   Material importedMaterial = xmlManager.xml2Material(xmlFile);
	        	   
	        	   if (importedMaterial != null) {
	        		   //Supplier from the XML
	        	        Supplier xmlSupplier = importedMaterial.getSupplier();
	        	        //Check if this supplier exists in the db
	        	        Supplier dbSupplier = supplierManager.getSupplierByEmail(xmlSupplier.getEmail());
	        	        
	        	        if (dbSupplier != null) {
	        	        	// Use the existing db supplier
	        	            importedMaterial.setSupplier(dbSupplier);
	        	            System.out.println("Material linked to existing supplier: " + dbSupplier.getEmail());
	        	        }else {
	        	        	// Fallback to current user
	        	            Supplier currentSupplier = supplierManager.getSupplierByEmail(email);
	        	            importedMaterial.setSupplier(currentSupplier);
	        	            System.out.println("Supplier from XML not found. Assigned material to current supplier: " + currentSupplier.getEmail());
	        	        }
	        	        //add material
	        	        materialManager.addMaterial(importedMaterial);
	        	        System.out.println("Import successful.");
	        	   } else {
	        	        System.out.println("Failed to import material from XML.");
	        	    }
	        	   
	        	   materialMenu(email, role);
	        	   break;
	           case 7:
	        	   currentUser =null;
	        	   main(null);
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