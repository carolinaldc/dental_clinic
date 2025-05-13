package dentalClinicPOJOS;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import dentalClinicXMLUtils.SQLDateAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Patient")
@XmlType(propOrder = {"name", "surname", "dob","email", "credit_card", "clinician"})
public class Patient implements Serializable {

	private static final long serialVersionUID = -2651925648311189667L;
	@XmlTransient
	private Integer patient_id;
	@XmlElement
	private String name; 
	@XmlElement
	private String surname; 
	@XmlJavaTypeAdapter(SQLDateAdapter.class)
	private Date dob; 
	@XmlAttribute
	private Integer phone;
	@XmlElement
	private String email; 
	@XmlAttribute
	private Emergency emergency; 
	@XmlElement
	private Integer credit_card; 
	@XmlElement
	private Clinician clinician;
	@XmlTransient
	private List<PatientTreatment> appointment;
	
	public enum Emergency {
		LOW, MEDIUM, HIGH
	}
	
	public Patient() {
		super();
		appointment = new ArrayList<PatientTreatment>();
	}
	
	public Patient(String name, String surname, Date dob, Integer phone, String email, Integer credit_card, Emergency urgency) {
		super();
		this.name = name;
		this.surname = surname;
		this.dob = dob;
		this.phone = phone;
		this.email = email;
		this.credit_card = credit_card;
		this.emergency = urgency;
	}
	
	

	public Integer getId() {
		return patient_id;
	}


	public void setId(Integer id) {
		this.patient_id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCredit_card() {
		return credit_card;
	}

	public void setCredit_card(int credit_card) {
		this.credit_card = credit_card;
	}


	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}


	public Emergency getUrgency() {
		return emergency;
	}

	public void setUrgency(Emergency urgency) {
		this.emergency = urgency;
	}

	
	
	public Clinician getClinician() {
		return clinician;
	}

	public void setClinician(Clinician clinician) {
		this.clinician = clinician;
	}

	public List<PatientTreatment> getAppointment() {
		return appointment;
	}

	public void setAppointment(List<PatientTreatment> appointment) {
		this.appointment = appointment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(appointment, clinician, credit_card, dob, email, patient_id, name, phone, surname, emergency);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		return Objects.equals(appointment, other.appointment) && Objects.equals(clinician, other.clinician)
				&& Objects.equals(credit_card, other.credit_card) && Objects.equals(dob, other.dob)
				&& Objects.equals(email, other.email) && Objects.equals(patient_id, other.patient_id)
				&& Objects.equals(name, other.name) && Objects.equals(phone, other.phone)
				&& Objects.equals(surname, other.surname) && emergency == other.emergency;
	}

	@Override
	public String toString() {
		return "Patient [name=" + name + ", surname=" + surname + ", phone=" + phone + ", email=" + email
				+ ", credit_card=" + credit_card + ", urgency=" + emergency + ", dob=" + dob + ", clinician=" + clinician
				+ "]";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
