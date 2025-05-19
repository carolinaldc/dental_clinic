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


public class Patient implements Serializable {

	private static final long serialVersionUID = -2651925648311189667L;

	private Integer patient_id;
	private String name; 
	private String surname; 
	private Date dob; 
	private Integer phone;
	private String email; 
	private Integer credit_card; 
	private List<Appointment> appointments;
	
	public Patient() {
		super();
	}

	public Patient(String name, String surname, Date dob, Integer phone, String email, Integer credit_card,
			List<Appointment> appointments) {
		super();
		this.name = name;
		this.surname = surname;
		this.dob = dob;
		this.phone = phone;
		this.email = email;
		this.credit_card = credit_card;
		this.appointments = appointments;
	}

	public Integer getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(Integer patient_id) {
		this.patient_id = patient_id;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getCredit_card() {
		return credit_card;
	}

	public void setCredit_card(Integer credit_card) {
		this.credit_card = credit_card;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	@Override
	public int hashCode() {
		return Objects.hash(appointments, credit_card, dob, email, name, patient_id, phone, surname);
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
		return Objects.equals(appointments, other.appointments) && Objects.equals(credit_card, other.credit_card)
				&& Objects.equals(dob, other.dob) && Objects.equals(email, other.email)
				&& Objects.equals(name, other.name) && Objects.equals(patient_id, other.patient_id)
				&& Objects.equals(phone, other.phone) && Objects.equals(surname, other.surname);
	}

	@Override
	public String toString() {
		return "Patient [name=" + name + ", surname=" + surname + ", dob=" + dob + ", phone=" + phone + ", email="
				+ email + ", credit_card=" + credit_card + ", appointments=" + appointments + "]";
	}
}
