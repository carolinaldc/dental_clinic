package dentalClinicIFaces;

import java.util.List;

import dentalClinicPOJOS.Supplier;

public interface SupplierManager {
	public void addSupplier(Supplier supplier);
	public void deleteSupplier(Integer supplier_id);
	public void deleteSupplierByEmail(String email);
	public void updateSupplier(Integer supplier_id);
	public void updateSupplier(Integer supplier_id, String fieldName, Object value);

	public Supplier getSupplierByid(Integer supplier_id);
	public List<Supplier> getListOfSuppliers();
	public Supplier getSupplierByEmail(String email);
	public Supplier getSupplierOfMaterial(Integer material_id);
	
}