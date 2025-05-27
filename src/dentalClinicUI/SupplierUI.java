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
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.Treatment;


public class SupplierUI {

	private BufferedReader reader;
	private SupplierManager supplierManager;
    private Supplier currentSupplier;
    private MaterialManager materialManager;
    private MaterialUI materialUI;
	
	public SupplierUI(SupplierManager supplierManager, MaterialManager materialManager,BufferedReader reader) {
		this.supplierManager = supplierManager;
        this.materialManager = materialManager;
		this.reader = reader;
	}
	
	public SupplierUI(SupplierManager supplierManager) {
		this.supplierManager = supplierManager;
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
        try {
        	viewSupplierList();
            System.out.println("Enter ID of supplier to delete:");
            int id = Integer.parseInt(reader.readLine());

            supplierManager.deleteSupplier(id);
            System.out.println("Supplier deleted");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input");
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

            int choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                    System.out.println("Enter new name (" + supplier.getSupplierName() + "):");
                    String newName = reader.readLine();
                    if (!newName.trim().isEmpty()) {
                    	supplierManager.updateSupplier(supplier.getSupplier_id(), "name", newName);
                    }
                    break;
                case 2:
                    System.out.println("Enter new phone (" + supplier.getPhone() + "):");
                    String newPhone = reader.readLine();
                    if (!newPhone.trim().isEmpty()) {
                    	supplierManager.updateSupplier(supplier.getSupplier_id(), "phone", newPhone);
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
