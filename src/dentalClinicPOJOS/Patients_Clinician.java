package dentalClinicPOJOS;
import java.io.Serializable; 
import java.sql.Date; 
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Patients_Clinician")
@XmlType(propOrder = {"visitInfo","date","clinician","patient"})
public class Patients_Clinician implements Serializable{

	private static final long serialVersionUID = 7404145314772486159L;
	@XmlElement
	private Patient patient ;  
	@XmlElement
	private Clinician clinician ;  
	@XmlElement
	private Date date ;  
	@XmlAttribute
	private String visitInfo ;  
	@XmlTransient
	private Integer id ;
	
	public Patients_Clinician(String visitInfo, Date date, Clinician clinician, Patient patient) {
		super();
        this.visitInfo = visitInfo;
        this.date = date;
        this.clinician = clinician;
        this.patient = patient;
	}

	
	
	public Patient getPatient() {
		return patient;
	}



	public void setPatient(Patient patient) {
		this.patient = patient;
	}



	public Clinician getClinician() {
		return clinician;
	}



	public void setClinician(Clinician clinician) {
		this.clinician = clinician;
	}



	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getVisitInfo() {
		return visitInfo;
	}

	public void setVisitInfo(String visitInfo) {
		this.visitInfo = visitInfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	@Override
	public int hashCode() {
		return Objects.hash(clinician, date, id, patient, visitInfo);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patients_Clinician other = (Patients_Clinician) obj;
		return Objects.equals(clinician, other.clinician) && Objects.equals(date, other.date)
				&& Objects.equals(id, other.id) && Objects.equals(patient, other.patient)
				&& Objects.equals(visitInfo, other.visitInfo);
	}


	@Override
	public String toString() {
		return "Patients_Clinician [patient=" + patient + ", clinician=" + clinician + ", date=" + date + ", visitInfo="
				+ visitInfo + "]";
	}

	
	
	
	
	
	
}
