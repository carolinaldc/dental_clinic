package dentalClinicUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import dentalClinicPOJOS.Supplier;

import dentalClinicIFaces.MaterialManager;
import dentalClinicIFaces.SupplierManager;
import dentalClinicIFaces.TreatmentManager;
import dentalClinicIFaces.UserManager;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.Treatment;
import dentalClinicPOJOS.User;


public class SupplierUI {

	private BufferedReader reader;
	private SupplierManager supplierManager;
    private Supplier currentSupplier;
    private MaterialManager materialManager;
    private MaterialUI materialUI;
    private UserManager usermanager;
	
	public SupplierUI(SupplierManager supplierManager, MaterialManager materialManager,BufferedReader reader) {
		this.supplierManager = supplierManager;
        this.materialManager = materialManager;
		this.reader = reader;
	}
	
	public SupplierUI(SupplierManager supplierManager, UserManager usermanager) {
		this.supplierManager = supplierManager;
		this.usermanager = usermanager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
	}

	public void setCurrentSupplier(Supplier supplier) {
        this.currentSupplier = supplier;
    }
	public void addSupplier(String email) {
        try {
            System.out.println("Enter supplier name:");
            String supplierName = reader.readLine();

            System.out.println("Enter phone:");
            int phone = Integer.parseInt(reader.readLine());


            List<Material> materials = new ArrayList<>(); //add materials later on
            
            Supplier supplier = new Supplier(supplierName, phone, email, materials);
            supplierManager.addSupplier(supplier);

            System.out.println("Supplier added successfully.");

        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error adding Supplier.");
            e.printStackTrace();
        }
        
    }
	
	
	 public void deleteSupplier() {
	        if (currentSupplier== null) {
	            System.out.println("No supplier logged in.");
	            return;
	        }
	        try {
	            supplierManager.deleteSupplier(currentSupplier.getSupplier_id());
	            System.out.println("Your supplier profile has been deleted.");
	            currentSupplier= null;
	        } catch (Exception e) {
	            System.out.println("Error deleting clinician.");
	            e.printStackTrace();
	        }
	    }
	
    public void deleteSupplierByEmail() {
        if (currentSupplier== null) {
            System.out.println("No supplier logged in.");
            return;
        }
        try {
        	supplierManager.deleteSupplierByEmail(currentSupplier.getEmail());
            System.out.println("Your supplier profile has been deleted.");
            currentSupplier= null;
        } catch (Exception e) {
            System.out.println("Error deleting supplier.");
            e.printStackTrace();
        }
    }
	
	public void viewSupplierList() {
        try {
           List<Supplier> suppliers = supplierManager.getListOfSuppliers();
           if (suppliers != null && !suppliers.isEmpty()) {
                for (Supplier supplier : suppliers) {
                    System.out.println(supplier);
                }
            } else {
                System.out.println("No suppliers found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
		

    
    public void modifySupplier() {
        if (currentSupplier == null) {
            System.out.println("No supplier logged in.");
            return;
        }

        try {
        	Supplier supplier = currentSupplier;

            System.out.println("What do you want to modify:");
            System.out.println("1. Name");
            System.out.println("2. Phone");
            System.out.println("3. Email");
            System.out.println("4. Password");
            

            int choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                	System.out.println("Enter new name (" + supplier.getSupplierName() + "):");
                    String newName = reader.readLine().trim();
                    if (!newName.isEmpty()) {
                        supplierManager.updateName(supplier.getSupplier_id(), newName);
                        supplier.setSupplierName(newName);
                    }
                    break;
                case 2:
                	System.out.println("Enter new phone (" + supplier.getPhone() + "):");
                    String newPhoneInput = reader.readLine().trim();
                    if (!newPhoneInput.isEmpty()) {
                        try {
                            int newPhone = Integer.parseInt(newPhoneInput);
                            supplierManager.updatePhone(supplier.getSupplier_id(), newPhone);
                            supplier.setPhone(newPhone); // update local object
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid phone number. Please enter numeric digits only.");
                        }
                    }
                    break;
                case 3:
                	System.out.println("Enter new email (" + supplier.getEmail() + "):");
                    String newEmail = reader.readLine();

                    if (!newEmail.trim().isEmpty()) {
                        // Check if email already exists
                        if (usermanager.getUserByEmail(newEmail) != null) {
                            System.out.println("Email already in use. Try a different one.");
                        } else {
                            // Update in the users table
                            User u = usermanager.getUserByEmail(supplier.getEmail());
                            usermanager.changeEmail(u, newEmail);  

                            supplier.setEmail(newEmail);
                            supplierManager.updateSupplierEmail(supplier.getSupplier_id(), newEmail);
                            
                            currentSupplier = supplier;
                            System.out.println("Email updated successfully.");
                        }
                    }
                    break;
                case 4:
                	System.out.println("Enter new password:");
                    String newPassword = reader.readLine();
                    
                    if (!newPassword.trim().isEmpty()) {
                        User u = usermanager.getUserByEmail(supplier.getEmail());
                        if (u != null) {
                            usermanager.changePassword(u, newPassword);
                            System.out.println("Password updated successfully.");
                        } else {
                            System.out.println("User not found.");
                        }
                    } else {
                        System.out.println("Password cannot be empty.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }
            System.out.println("Supplier updated.");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error modifying the supplier");
            e.printStackTrace();
        }
    }

	
}
