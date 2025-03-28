package dentalClinicPOJOS;

import java.io.Serializable;
import java.sql.Date;

public class Patient implements Serializable {

	private static final long serialVersionUID = -2651925648311189667L;
	private  int id; 
	private String dni;
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

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
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
	
	
	
	
	
	
	
	
	
	

}
