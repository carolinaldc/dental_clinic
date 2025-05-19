package dentalClinicIFaces;

import java.util.List;

import dentalClinicPOJOS.Supplier;

public interface SupplierManager {
	public void addSupplier(Supplier supplier);
	public void deleteSupplier(Integer supplier_id);
	public void updateSupplier(Integer supplier_id);

	public Supplier getSupplierByid(Integer supplier_id);
	public List<Supplier> getListOfSuppliers();
}