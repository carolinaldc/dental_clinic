package dentalClinicJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCManager {
	
	private Connection c = null;
	
	public JDBCManager() {
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/Dental_Clinic.db");
			System.out.println("Database connection opened");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			System.out.print("Libraries not loaded");
		}
			
	}
		
	private void createTables() {
		
		try {
			Statement stmt = c.createStatement();
			
			String sql = "CREATE TABLE Clinicians("
					+ "clinician_id	INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name	TEXT,"
					+ "surname TEXT,"
					+ "specialty TEXT,"
					+ "phone INTEGER NOT NULL "
					+ "email TEXT )";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE Patients("
					+ "patient_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT,"
					+ "surname TEXT,"
					+ "dob DATE,"
					+ "phone INTEGER,"
					+ "email TEXT,"
					+ "emergency TEXT,"
					+ "credit_card	INTEGER)";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE Materials("
					+ "materials_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name	TEXT,"
					+ "stock  INTEGER)";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE treatments("
					+ "treatment_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name	TEXT,"
					+ "description TEXT,"
					+ "price NUMERIC,"
					+ "room_id INTEGER REFERENCES Rooms(room_id) NOT NULL)";
			stmt.executeUpdate(sql);

			
			sql = "CREATE TABLE Clinicians_treatments("
					+ "clinician_appointment_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "treatment_id INTEGER REFERENCES Treatments(treatment_id) NOT NULL,"
					+ "clinician_id	INTEGER REFERENCES Clinicians(clinician_id) NOT NULL,";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE Patient_treatments("
					+ "Patient_appointment_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "patient_id INTEGER REFERENCES Patients(patient_id) NOT NULL,"
					+ "treatment_id	INTEGER REFERENCES Treatments(treatment_id) NOT NULL,"
					+ "date	DATE,"
					+ "comments TEXT)";			
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE Patients_clinicians("
					+ "patient_id INTEGER REFERENCES Patients(patient_id) NOT NULL,"
					+ "clinician_id	INTEGER REFERENCES Clinicians(clinician_id) NOT NULL,"
					+ "date	DATE,"
					+ "visit_info TEXT,"
					+ "	PRIMARY KEY(patient_id, clinician_id) )";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE Payments("
					+ "payment_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "amount NUMERIC,"
					+ "payment_date DATE,"
					+ "payment_method TEXT,"
					+ "status TEXT)";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE Rooms("
					+"room_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "status TEXT)";
			stmt.executeUpdate(sql);
			
			
			sql = "CREATE TABLE Treatment_materials("
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "treatment_id	INTEGER REFERENCES Treatments(treatment_id) NOT NULL,"
					+ "materials_id	INTEGER REFERENCES Materials(materials_id) NOT NULL,"
					+ "description TEXT,"
					+ "tools TEXT)";
			stmt.executeUpdate(sql);	
			
			sql = "CREATE TABLE Suppliers(" 
					+ "supplier_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name	TEXT,"
					+ "surname TEXT,"
					+ "phone INTEGER,"
					+ "email TEXT,"
					+ "materials_id	INTEGER REFERENCES Materials(material_id) NOT NULL )";
			stmt.executeUpdate(sql);



		}catch(SQLException e) {
			
			if(!e.getMessage().contains("already exists"))
				e.printStackTrace();
		}	

}
}
