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


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Clinician")
@XmlType(propOrder = {"name","surname","speciality", "email","phone"})
public class Clinician implements Serializable {
		
		private static final long serialVersionUID = -2637321372475755L;
		@XmlTransient
		private Integer clinician_id; 
		@XmlElement
		private String name; 
		@XmlElement
		private String surname; 
		@XmlAttribute
		private String speciality;
		@XmlElement
		private Integer phone;
		@XmlElement
		private String email; 
		@XmlTransient
		private List<Patient> patients;
		@XmlTransient
		private List<Treatment> treatments;
		@XmlTransient
		private List<Patients_Clinician> appointments;

		
		
		public Clinician() {
			super();
			patients = new ArrayList<Patient>();
			treatments = new ArrayList<Treatment>();
		}
		
		public Clinician(Integer clinician_id, String name, String surname, String speciality, String email, Integer phone) {
			super();
			this.clinician_id = clinician_id;
			this.name = name;
			this.surname = surname;
			this.speciality = speciality;
			this.email = email;
			this.phone = phone;
		
		}
		
		public Clinician(String name, String surname, String speciality, String email, Integer phone) {
			super();
			this.name = name;
			this.surname = surname;
			this.speciality = speciality;
			this.email = email;
			this.phone = phone;
		
		}

		public Clinician(String name, String surname, String speciality, Integer phone) {
			super();
			this.name = name;
			this.surname = surname;
			this.speciality = speciality;
			this.phone = phone;
		}

		public Clinician(Integer id, String name, String speciality) {
			super();
			this.name = name;
			this.speciality = speciality;
			this.clinician_id = id;
		}

		public Integer getClinicianId() {
		    return clinician_id;
		}

		public void setClinicianId(Integer clinician_id) {
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


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}


		public int getPhone() {
			return phone;
		}


		public void setPhone(int phone) {
			this.phone = phone;
		}


		public String getSpeciality() {
			return speciality;
		}


		public void setSpeciality(String speciality) {
			this.speciality = speciality;
		}
		
		

		public List<Patient> getPatients() {
			return patients;
		}


		public void setPatients(List<Patient> patients) {
			this.patients = patients;
		}


		public List<Treatment> getTreatments() {
			return treatments;
		}


		public void setTreatments(List<Treatment> treatments) {
			this.treatments = treatments;
		}



		@Override
		public int hashCode() {
			return Objects.hash(email, clinician_id, name, patients, phone, speciality, surname, treatments);
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
			return Objects.equals(email, other.email) && clinician_id == other.clinician_id && Objects.equals(name, other.name)
					&& Objects.equals(patients, other.patients) && phone == other.phone
					&& Objects.equals(speciality, other.speciality) && Objects.equals(surname, other.surname)
					&& Objects.equals(treatments, other.treatments);
		}


		@Override
		public String toString() {
			return "Clinician [id=" + clinician_id + ", name=" + name + ", surname=" + surname + ", speciality=" + speciality + ", email=" + email + ", phone="
					+ phone + ", patients=" + patients + ", treatments=" + treatments
					+ "]";
		}	

	}