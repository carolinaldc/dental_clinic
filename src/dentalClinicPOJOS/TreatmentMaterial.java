package dentalClinicPOJOS;

import java.io.Serializable;
import java.util.Objects;

public class TreatmentMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private Material material;
    private Treatment treatment;
    private String tools;
    private String description;

    public TreatmentMaterial(Material material, Treatment treatment, String tools, String description) {
        super();
        this.material = material;
        this.treatment = treatment;
        this.tools = tools;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Material getMaterial() {
        return material;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, material, treatment, tools, description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof TreatmentMaterial))
            return false;
        TreatmentMaterial other = (TreatmentMaterial) obj;
        return id == other.id &&
               material == other.material &&
               treatment == other.treatment &&
               Objects.equals(tools, other.tools) &&
               Objects.equals(description, other.description);
    }

    @Override
    public String toString() {
        return "TreatmentMaterial [id=" + id + ", material_id=" + material +
               ", treatment_id=" + treatment + ", tools=" + tools +
               ", description=" + description + "]";
    }
}
