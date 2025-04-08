package dentalClinicPOJOS;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class PatientTreatment implements Serializable {

    private static final long serialVersionUID = -2637321372475755L;

    private int id;
    private Patient patient_id;
    private Treatment treatment_id;
    private Date date;
    private String comment;

    public PatientTreatment() {
        super();
    }

    public int getId() {
        return id;
    }

    public Patient getPatient_id() {
        return patient_id;
    }


    public Treatment getTreatment_id() {
        return treatment_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patient_id, treatment_id, date, comment);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof PatientTreatment))
            return false;
        PatientTreatment other = (PatientTreatment) obj;
        return id == other.id &&
               patient_id == other.patient_id &&
               treatment_id == other.treatment_id &&
               Objects.equals(date, other.date) &&
               Objects.equals(comment, other.comment);
    }

    @Override
    public String toString() {
        return "PatientTreatment [id=" + id + ", patient_id=" + patient_id + ", treatment_id=" + treatment_id +
               ", date=" + date + ", comment=" + comment + "]";
    }
}
