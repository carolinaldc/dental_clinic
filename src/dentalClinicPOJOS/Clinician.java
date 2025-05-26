package dentalClinicPOJOS;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Clinician")
@XmlType(propOrder = {"name", "surname", "specialty","phone", "email"})
public class Clinician implements Serializable {
		
		private static final long serialVersionUID = -2637321372475755L;
		
		@XmlTransient
		private Integer clinician_id; 
		@XmlElement
		private String name; 
		@XmlElement
		private String surname; 
		@XmlAttribute
		private String specialty;
		@XmlElement
		private Integer phone;
		@XmlElement
		private String email; 
		@XmlTransient
		private List<Appointment> appointments;

		public Clinician() {
			super();
		}

		public Clinician(String name, String surname, String speciality, Integer phone, String email,
				List<Appointment> appointments) {
			super();
			this.name = name;
			this.surname = surname;
			this.specialty = speciality;
			this.phone = phone;
			this.email = email;
			this.appointments = appointments;
		}

		
		
		public Clinician(Integer clinician_id, String name, String surname, String speciality, Integer phone,
				String email, List<Appointment> appointments) {
			super();
			this.clinician_id = clinician_id;
			this.name = name;
			this.surname = surname;
			this.specialty = speciality;
			this.phone = phone;
			this.email = email;
			this.appointments = appointments;
		}

		public Integer getClinician_id() {
			return clinician_id;
		}

		public void setClinician_id(Integer clinician_id) {
			this.clinician_id = clinician_id;
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

		public String getSpecialty() {
			return specialty;
		}

		public void setSpeciality(String speciality) {
			this.specialty = speciality;
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

		public List<Appointment> getAppointments() {
			return appointments;
		}

		public void setAppointments(List<Appointment> appointments) {
			this.appointments = appointments;
		}

		@Override
		public int hashCode() {
			return Objects.hash(appointments, clinician_id, email, name, phone, specialty, surname);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Clinician other = (Clinician) obj;
			return Objects.equals(appointments, other.appointments) && Objects.equals(clinician_id, other.clinician_id)
					&& Objects.equals(email, other.email) && Objects.equals(name, other.name)
					&& Objects.equals(phone, other.phone) && Objects.equals(specialty, other.specialty)
					&& Objects.equals(surname, other.surname);
		}

		@Override
		public String toString() {
			return "Clinician id: " + clinician_id + "[ Name: " + name + ", Surname: " + surname + ", Specialty: " + specialty + "]";
		}
		/*
		 * public String toString() {
			return "Clinician [name=" + name + ", surname=" + surname + ", specialty=" + specialty + ", phone="
					+ phone + ", email=" + email + ", appointments=" + appointments + "]";
		}
		 */
	}