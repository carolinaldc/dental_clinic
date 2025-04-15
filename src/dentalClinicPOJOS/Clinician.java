package dentalClinicPOJOS;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class Clinician implements Serializable {
		
		private static final long serialVersionUID = -2637321372475755L;
		private Integer id ; 
		private String name ; 
		private String surname ; 
		private String email ; 
		private Integer phone ; 
		private String speciality;
		private List<Patient> patients;
		private List<Treatment> treatments;
		
		
		public Clinician() {
			super();
			patients = new ArrayList<Patient>();
			treatments = new ArrayList<Treatment>();
		}


		public int getId() {
			return id;
		}
		
		public void setId(Integer id) {
			this.id = id;
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
			return Objects.hash(email, id, name, patients, phone, speciality, surname, treatments);
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
			return Objects.equals(email, other.email) && id == other.id && Objects.equals(name, other.name)
					&& Objects.equals(patients, other.patients) && phone == other.phone
					&& Objects.equals(speciality, other.speciality) && Objects.equals(surname, other.surname)
					&& Objects.equals(treatments, other.treatments);
		}


		@Override
		public String toString() {
			return "Clinician [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", phone="
					+ phone + ", speciality=" + speciality + ", patients=" + patients + ", treatments=" + treatments
					+ "]";
		}


		
		

	}