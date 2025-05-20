package dentalClinicUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.MaterialManager;
import dentalClinicIFaces.TreatmentManager;
import dentalClinicPOJOS.Appointment;
import dentalClinicPOJOS.Material;
import dentalClinicPOJOS.Treatment;

public class TreatmentUI {

    private TreatmentManager treatmentManager;
    private MaterialManager materialManager;
    private MaterialUI materialUI;
    private BufferedReader reader;

    public TreatmentUI(TreatmentManager treatmentManager, MaterialManager materialManager, BufferedReader reader) {
        this.treatmentManager = treatmentManager;
        this.materialManager = materialManager;
        this.reader = reader;
    }

    public void addTreatment() {
        try {
            System.out.println("Enter treatment name:");
            String name = reader.readLine();

            System.out.println("Enter description:");
            String description = reader.readLine();

            System.out.println("Enter price:");
            int price = Integer.parseInt(reader.readLine());

            List<Material> selectedMaterials = new ArrayList<>();

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

            List<Appointment> appointments = new ArrayList<>(); // No appointments added, bc its appointment that adds the treatment

            Treatment treatment = new Treatment(name, description, price, appointments, selectedMaterials);
            treatmentManager.addTreatment(treatment);

            System.out.println("Treatment added successfully.");

        } catch (IOException e) {
            System.out.println("Error reading input.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number for price.");
        }
    }

    public void deleteTreatment() { 
        try {
        	viewTreatmentsList();
            System.out.println("Enter ID of treatment to delete:");
            int id = Integer.parseInt(reader.readLine());

            treatmentManager.deleteTreatment(id);
            System.out.println("Treatment deleted.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
    

    public void modifyTreatment() {
        try {
        	viewTreatmentsList();
        		
        		System.out.println("Enter treatment ID to modify:");
                int treatmentId = Integer.parseInt(reader.readLine());

                Treatment treatmentToModify = treatmentManager.getTreatmentById(treatmentId);
                int choice=0;
        		try {
        			System.out.println("What do you want to modify: ");
    	            System.out.println("1. name"); 
    	            System.out.println("2. description"); 
    	            System.out.println("3. price");  
    	            System.out.println("4. Materials");


    				choice = Integer.parseInt(reader.readLine());

    				switch (choice) {
    					case 1:
    			            System.out.println("Enter new name for the treatment:");
    			            String newName = reader.readLine();
    			            treatmentToModify.setName(newName);
    						break;
    					case 2:
    			            System.out.println("Enter new description for the treatment:");
    			            String newDescription = reader.readLine();
    			            treatmentToModify.setDescription(newDescription);
    						break;
    					case 3:
    			            System.out.println("Enter new price for the treatment:");
    			            int newPrice = Integer.parseInt(reader.readLine());
    			            treatmentToModify.setPrice(newPrice);
    						break;
    					case 4:
    			            System.out.println("Enter new materials for the treatment:");
    			            List<Material> selectedMaterials = new ArrayList<>();

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
    			            treatmentToModify.setMaterials(selectedMaterials);
    						break;
    				}
        		}catch(Exception e){
        			e.printStackTrace();
        		}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

    public void viewTreatmentsList() {
        try {
           List<Treatment> treatments = treatmentManager.getListOfTreatments();
           if (treatments != null && !treatments.isEmpty()) {
                for (Treatment treatment : treatments) {
                    System.out.println(treatment);
                }
            } else {
                System.out.println("No treatments found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}
