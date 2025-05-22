package dentalClinicJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import dentalClinicIFaces.TreatmentManager;

public class JDBCManager {
	
	private Connection c = null;
	
	public JDBCManager() {
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/DentalClinic.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			
		
			System.out.println("Database connection opened");
			createTables();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			System.out.print("Libraries not loaded");
		}
			
	}
		
	private void createTables() {
		
		try {
			
			Statement stmt = c.createStatement();

			
			String sql = "CREATE TABLE Clinicians ("
					+ "clinician_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT,"
					+ "surname TEXT,"
					+ "specialty TEXT,"
					+ "phone TEXT,"
					+ "email TEXT)";
			stmt.executeUpdate(sql);
			

			
			sql = "CREATE TABLE Patients ("
					+ "patient_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT,"
					+ "surname TEXT,"
					+ "dob TEXT,"
					+ "phone INTEGER,"
					+ "email TEXT,"
					+ "credit_card INTEGER)";
			stmt.executeUpdate(sql);

			
			sql = "CREATE TABLE Materials ("
					+ "materials_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "supplier_id INTEGER REFERENCES Suppliers(supplier_id) NOT NULL,"
					+ "name TEXT)";
			stmt.executeUpdate(sql);

			
			sql = "CREATE TABLE Treatments ("
					+ "treatment_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT,"
					+ "description TEXT,"
					+ "price NUMERIC)";
			stmt.executeUpdate(sql);

			
			sql = "CREATE TABLE Appointments ("
					+ "appointment_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "patient_id INTEGER REFERENCES Patients(patient_id) NOT NULL,"
					+ "treatment_id INTEGER REFERENCES Treatments(treatment_id) NOT NULL,"
					+ "clinician_id INTEGER REFERENCES Clinicians(clinician_id) NOT NULL,"
					+ "date Date,"
					+ "comments TEXT)";
			stmt.executeUpdate(sql);

			
			sql = "CREATE TABLE Treatment_materials ("
					+ "treatment_id INTEGER REFERENCES Treatments(treatment_id) NOT NULL,"
					+ "materials_id INTEGER REFERENCES Materials(materials_id) NOT NULL,"
					+ "PRIMARY KEY (treatment_id, materials_id))";
			stmt.executeUpdate(sql);	

			
			sql = "CREATE TABLE Suppliers (" 
					+ "supplier_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "supplierName TEXT,"
					+ "phone INTEGER,"
					+ "email TEXT)";
			stmt.executeUpdate(sql);

		
			sql = "INSERT INTO Patients (patient_id, name, surname, dob, phone, email, credit_card) "
					+ "VALUES (2341, 'Jorge', 'Gonzalez', '1992-06-23', '34684027863', 'jorgegonzalez@gmail.com', '5223935698626682')";
			stmt.executeUpdate(sql);

			
			sql = "INSERT INTO Clinicians (clinician_id, name, surname, specialty, phone, email) "
					+ "VALUES (7823, 'Julia', 'Velazquez', 'Surgery', '34692438522', 'juliavelazquez@gmail.com')";
			stmt.executeUpdate(sql);


			sql = "INSERT INTO Treatments (treatment_id, name, description, price, room_id) "
					+ "VALUES (2003, 'Fenestration', 'Palate surgery', 200.00, 2823)";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO Appointments (appointment_id, patient_id, treatment_id, clinician_id, date, comments) "
					+ "VALUES (2003, 5053, 1234, 2345, 2025-12-01, 'tooth extraction')";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO Suppliers (supplier_id, supplierName, phone, email) "
					+ "VALUES (8963, 'CorteIngles', '34623985233', 'robertohernandez@gmail.com')";
			stmt.executeUpdate(sql);


			sql = "INSERT INTO Materials (materials_id, supplier_id ,name) "
					+ "VALUES (4421, 1234 ,'Dental mirror')";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO Treatment_materials (treatment_id, materials_id) "
					+ "VALUES (9873, 2341)";
			stmt.executeUpdate(sql);

			System.out.println("Tables created and default values inserted");
			
			

		}catch(SQLException e) {
			
			if(!e.getMessage().contains("already exists"))
				e.printStackTrace();
		}	

	}
	
	public Connection getConnection() {
		return c;
	}
	
	public void closeConnection() {
		try {		
			c.close();
		}catch(SQLException e)
		{	
			e.printStackTrace();
		}
	}

	
}
