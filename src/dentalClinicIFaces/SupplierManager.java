package dentalClinicIFaces;

import java.util.List;
import dentalClinicPOJOS.Supplier;

public interface SupplierManager {
    void addSupplier(Supplier supplier);
    List<Supplier> getAllSuppliers();
    Supplier getSupplierById(int supplierId);
    void deleteSupplier(int supplierId);
}