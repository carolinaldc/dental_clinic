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
@XmlType(propOrder = { "name", "surname", "dob", "phone", "email", "credit_card", "payment" })


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
	@XmlElement
	private Integer credit_card; 
	@XmlTransient
	private List<PatientTreatment> treatments;
	@XmlTransient
	private List<Patients_Clinician> appointments ; 
	@XmlTransient
	private Payment payment; 
	
	
	public Patient() {
		super();
		treatments = new ArrayList<PatientTreatment>();
	}
	
	public Patient(String name, String surname, Date dob, Integer phone, String email, Integer credit_card, List<PatientTreatment> treatments,List<Patients_Clinician> appointments) {
		super();
		this.name = name;
		this.surname = surname;
		this.dob = dob;
		this.phone = phone;
		this.email = email;
		this.credit_card = credit_card;
		this.treatments = treatments ; 
		this.appointments = appointments ; 
		
	}
	
	

	public Patient(String name, String surname, Date dob, Integer phone, String email, Integer credit_card) {
		super();
		this.name = name;
		this.surname = surname;
		this.dob = dob;
		this.phone = phone;
		this.email = email;
		this.credit_card = credit_card;
	}

	public Patient(String name, String surname, Date dob, Integer phone, Integer credit_card) {
		super();
		this.name = name;
		this.surname = surname;
		this.dob = dob;
		this.phone = phone;
		this.credit_card = credit_card;
	}
	
	public Patient(int patientId) {
		// TODO Auto-generated constructor stub
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
	

	public List<PatientTreatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(List<PatientTreatment> treatments) {
		this.treatments = treatments;
	}

	public List<Patients_Clinician> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Patients_Clinician> appointments) {
		this.appointments = appointments;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(treatments, credit_card, dob, email, name, patient_id, phone, surname, appointments);
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
		return Objects.equals(treatments, other.treatments) && Objects.equals(credit_card, other.credit_card)
				&& Objects.equals(dob, other.dob) && Objects.equals(email, other.email)
				&& Objects.equals(name, other.name) && Objects.equals(patient_id, other.patient_id)
				&& Objects.equals(phone, other.phone) && Objects.equals(surname, other.surname)
				&& Objects.equals(appointments, other.appointments);
	}


	@Override
	public String toString() {
		return "Patient [id=" + patient_id + ", name=" + name + ", surname=" + surname + ", dob=" + dob + ", phone=" + phone + ", email="
				+ email + ", credit_card=" + credit_card + ", appointment=" + treatments + ", appointments=" + appointments + "]";
	}
}
