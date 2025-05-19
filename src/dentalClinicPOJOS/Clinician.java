package dentalClinicPOJOS;
import java.io.Serializable;
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

public class Clinician implements Serializable {
		
		private static final long serialVersionUID = -2637321372475755L;
		
		private Integer clinician_id; 
		private String name; 
		private String surname; 
		private String speciality;
		private Integer phone;
		private String email; 

		private List<Appointment> appointments;

		public Clinician() {
			super();
		}

		public Clinician(String name, String surname, String speciality, Integer phone, String email,
				List<Appointment> appointments) {
			super();
			this.name = name;
			this.surname = surname;
			this.speciality = speciality;
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

		public String getSpeciality() {
			return speciality;
		}

		public void setSpeciality(String speciality) {
			this.speciality = speciality;
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
			return Objects.hash(appointments, clinician_id, email, name, phone, speciality, surname);
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
					&& Objects.equals(phone, other.phone) && Objects.equals(speciality, other.speciality)
					&& Objects.equals(surname, other.surname);
		}

		@Override
		public String toString() {
			return "Clinician [name=" + name + ", surname=" + surname + ", speciality=" + speciality + ", phone="
					+ phone + ", email=" + email + ", appointments=" + appointments + "]";
		}
	}