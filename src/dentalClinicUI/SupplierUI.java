package dentalClinicUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import dentalClinicIFaces.MaterialManager;
import dentalClinicIFaces.SupplierManager;
import dentalClinicIFaces.TreatmentManager;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Treatment;


public class SupplierUI {

	private BufferedReader reader;
	private SupplierManager supplierManager;
    private MaterialManager materialManager;
    private MaterialUI materialUI;
	
	public SupplierUI(SupplierManager supplierManager, MaterialManager materialManager,BufferedReader reader) {
		this.supplierManager = supplierManager;
        this.materialManager = materialManager;
		this.reader = reader;
	}
	
	public void addSupplier() {
        try {
            System.out.println("Enter supplier name:");
            String supplierName = reader.readLine();

            System.out.println("Enter phone:");
            int phone = Integer.parseInt(reader.readLine());
            
            System.out.println("Enter email:");
            String email = reader.readLine();


            List<Material> selectedMaterials = new ArrayList<>(); //add materials later on

            materialUI.viewMaterialsList();
            System.out.println("Enter the IDs of the materials to use (comma separated), or leave blank to skip:");
            String input = reader.readLine();

            if (!input.trim().isEmpty()) {
                String[] ids = input.split(",");
                for (String idStr : ids) {
                    try {
                        int id = Integer.parseInt(idStr.trim());
                        Material material = materialManager.getMaterialByid(id);
                        if (material != null) {
                            selectedMaterials.add(material);
                        } else {
                            System.out.println("No material found with ID: " + id);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format: " + idStr.trim());
                    }
                }
            }

            
            Supplier supplier = new Supplier(supplierName, phone, email, selectedMaterials);
            supplierManager.addSupplier(supplier);

            System.out.println("Treatment added successfully.");

        } catch (IOException e) {
            System.out.println("Error reading input.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number for price.");
        }
    }
	
}
