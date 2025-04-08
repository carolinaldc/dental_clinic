package dentalClinicPOJOS;

import java.io.Serializable;
import java.util.Objects;

public class TreatmentMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private Material material_id;
    private Treatment treatment_id;
    private String tools;
    private String description;

    public TreatmentMaterial() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Material getMaterial_id() {
        return material_id;
    }

    public Treatment getTreatment_id() {
        return treatment_id;
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
        return Objects.hash(id, material_id, treatment_id, tools, description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof TreatmentMaterial))
            return false;
        TreatmentMaterial other = (TreatmentMaterial) obj;
        return id == other.id &&
               material_id == other.material_id &&
               treatment_id == other.treatment_id &&
               Objects.equals(tools, other.tools) &&
               Objects.equals(description, other.description);
    }

    @Override
    public String toString() {
        return "TreatmentMaterial [id=" + id + ", material_id=" + material_id +
               ", treatment_id=" + treatment_id + ", tools=" + tools +
               ", description=" + description + "]";
    }
}
