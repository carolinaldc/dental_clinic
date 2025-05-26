package dentalClinicUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.MaterialManager;
import dentalClinicIFaces.SupplierManager;
import dentalClinicIFaces.TreatmentManager;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Supplier;
import dentalClinicPOJOS.Treatment;

public class MaterialUI {
    private MaterialManager materialManager;
    private SupplierManager supplierManager;
    private TreatmentManager treatmentManager;
    private static TreatmentUI treatmentUI;
    private BufferedReader reader;
	
    public MaterialUI(MaterialManager materialManager, BufferedReader reader) {
    	this.materialManager = materialManager;
    	this.reader = reader;
	}


	public void addMaterial() {
        try {
            System.out.println("Enter material name:");
            String name = reader.readLine();

            System.out.println("Enter supplier id:");
            int supplier_id = Integer.parseInt(reader.readLine());
            Supplier supplier = supplierManager.getSupplierByid(supplier_id);


            List<Treatment> selectedTreatments = new ArrayList<>();

            treatmentUI.viewTreatmentsList();
            System.out.println("Enter the IDs of the materials to use (comma separated), or leave blank to skip:");
            String input = reader.readLine();

            if (!input.trim().isEmpty()) {
                String[] ids = input.split(",");
                for (String idStr : ids) {
                    try {
                        int id = Integer.parseInt(idStr.trim());
                        Treatment treatment = treatmentManager.getTreatmentById(id);
                        if (treatment != null) {
                        	selectedTreatments.add(treatment);
                        } else {
                            System.out.println("No treatments found with ID: " + id);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format: " + idStr.trim());
                    }
                }
            }

            Material material = new Material(name, supplier, selectedTreatments);
            materialManager.addMaterial(material);

            System.out.println("Material added successfully.");

        } catch (IOException e) {
            System.out.println("Error reading input.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number for price.");
        }
    }

	
    public void deleteMaterial() {
        try {
        	viewMaterialsList();
            System.out.println("Enter ID of material to delete:");
            int id = Integer.parseInt(reader.readLine());

            materialManager.deleteMaterial(id);
            System.out.println("Material deleted.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
	

    public void modifyMaterial() {
        try {
        	viewMaterialsList();
        	System.out.println("Enter material ID to modify:");
            int materilId = Integer.parseInt(reader.readLine());

            Material materialToModify = materialManager.getMaterialByid(materilId);
            int choice=0;
    		try {
    			System.out.println("What do you want to modify: ");
	            System.out.println("1. name ");
	            System.out.println("2. supplier "); //do i show a list of suppliers? no right? so then i just make up an ID??
	            System.out.println("3. treatments"); 


				choice = Integer.parseInt(reader.readLine());

				switch (choice) {
					case 1:
			            System.out.println("Enter new name for the Material:");
			            String newName = reader.readLine();
			            materialToModify.setName(newName);
						break;
					case 2:
			            System.out.println("Enter new supplier for the Material:");
			            int supplier_id = Integer.parseInt(reader.readLine());
			            Supplier supplier = supplierManager.getSupplierByid(supplier_id);
			            materialToModify.setSupplier(supplier);
						break;
					case 3:
						List<Treatment> selectedTreatments = new ArrayList<>();

			            treatmentUI.viewTreatmentsList();
			            System.out.println("Enter the IDs of the materials to use (comma separated), or leave blank to skip:");
			            String input = reader.readLine();

			            if (!input.trim().isEmpty()) {
			                String[] ids = input.split(",");
			                for (String idStr : ids) {
			                    try {
			                        int id = Integer.parseInt(idStr.trim());
			                        Treatment treatment = treatmentManager.getTreatmentById(id);
			                        if (treatment != null) {
			                        	selectedTreatments.add(treatment);
			                        } else {
			                            System.out.println("No treatments found with ID: " + id);
			                        }
			                    } catch (NumberFormatException e) {
			                        System.out.println("Invalid ID format: " + idStr.trim());
			                    }
			                }
			            }
			            materialToModify.setTreatments(selectedTreatments);
						break;
				}
    		}catch(Exception e){
    			e.printStackTrace();
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    public boolean viewMaterialsList() {
        try {
            List<Material> materials = materialManager.getListOfMaterials();
            if (materials != null && !materials.isEmpty()) {
                for (Material material : materials) {
                    System.out.println(material);
                }
                return true;
            } else {
                System.out.println("No materials found.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
