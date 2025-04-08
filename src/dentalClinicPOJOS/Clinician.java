package dentalClinicPOJOS;
import java.io.Serializable;
import java.util.Objects;

public class Clinician implements Serializable {
		
		private static final long serialVersionUID = -2637321372475755L;
		private int id ; 
		private String name ; 
		private String surname ; 
		private String email ; 
		private int phone ; 
		private String speciality ;
		
		
		public Clinician() {
			super();
		}


		public int getId() {
			return id;
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


		

		@Override
		public int hashCode() {
			return Objects.hash(id, name, surname, email, phone, speciality);
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
			return Objects.equals(email, other.email) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
					&& phone == other.phone && Objects.equals(speciality, other.speciality)
					&& Objects.equals(surname, other.surname);
		}


		@Override
		public String toString() {
			return "Clinicians [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", phone=" + phone
					+ ", speciality=" + speciality + "]";
		}
		
		

	}