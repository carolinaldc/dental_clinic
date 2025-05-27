package dentalClinicUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.MaterialManager;
import dentalClinicIFaces.SupplierManager;
import dentalClinicIFaces.TreatmentManager;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Supplier;
import dentalClinicPOJOS.Treatment;

public class MaterialUI {
    private MaterialManager materialManager;
    private SupplierManager supplierManager;
    private TreatmentManager treatmentManager;
    private Supplier currentSupplier;
    private static TreatmentUI treatmentUI;
    private BufferedReader reader;
	
    public MaterialUI(MaterialManager materialManager, BufferedReader reader, Supplier supplier) {
        this.materialManager = materialManager;
        this.reader = reader;
        this.currentSupplier = supplier;
    }

    public void setCurrentSupplier(Supplier supplier) {
        this.currentSupplier = supplier;
    }

	public void addMaterial() {
        try {
            System.out.println("Enter material name:");
            String name = reader.readLine();

            Supplier supplier = currentSupplier;

            List<Treatment> treatments = new ArrayList<>(); //adds treatments later on

            Material material = new Material(name, supplier, treatments);
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
        	viewMaterialsList(); //you can see the materials of the supplier that logged in, not the entire materials list
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
	            System.out.println("2. treatments (this doesnt work YET!!)"); 


				choice = Integer.parseInt(reader.readLine());

				switch (choice) {
					case 1:
						System.out.println("Enter new name (" + materialToModify.getName() + "):");
	                    String newName = reader.readLine();
	                    if (!newName.trim().isEmpty()) {
	                        materialManager.updateMaterial(materialToModify.getMaterials_id(), "name", newName);
	                    }
						break;
					case 2:
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
        	List<Material> materials = materialManager.getListOfSupplier_Materials(currentSupplier.getSupplier_id());
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
    
    public boolean viewAllMaterialsList() {
        try {
        	List<Material> materials = materialManager.getListOfAllMaterials();
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
