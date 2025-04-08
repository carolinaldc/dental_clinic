package dentalClinicPOJOS;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Patient implements Serializable {

	private static final long serialVersionUID = -2651925648311189667L;
	private  int id;
	private String name; 
	private String surname; 
	private int phone; 
	private String email; 
	private int credit_card; 
	private int urgency; 
	private Date dob; 
	private Clinician id_clinicians;
	
	public Patient() {
		super();
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

	public int getUrgency() {
		return urgency;
	}

	public void setUrgency(int urgency) {
		this.urgency = urgency;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}


	@Override
	public int hashCode() {
		return Objects.hash(credit_card, dob, email, id, id_clinicians, name, phone, surname, urgency);
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
		return credit_card == other.credit_card && Objects.equals(dob, other.dob) && Objects.equals(email, other.email)
				&& id == other.id && Objects.equals(id_clinicians, other.id_clinicians)
				&& Objects.equals(name, other.name) && phone == other.phone && Objects.equals(surname, other.surname)
				&& urgency == other.urgency;
	
		
	
	}


	@Override
	public String toString() {
		return "Patient [name=" + name + ", surname=" + surname + ", phone=" + phone + ", email=" + email
				+ ", credit_card=" + credit_card + ", urgency=" + urgency + ", dob=" + dob + "]";
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
