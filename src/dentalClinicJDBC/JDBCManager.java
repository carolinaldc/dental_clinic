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
			c = DriverManager.getConnection("jdbc:sqlite:./db/Clinica_Dental.db");
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
					+ "email TEXT UNIQUE NOT NULL)";
			stmt.executeUpdate(sql);

			
			sql = "CREATE TABLE Patients ("
					+ "patient_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT,"
					+ "surname TEXT,"
					+ "dob TEXT,"
					+ "phone INTEGER,"
					+ "email TEXT UNIQUE NOT NULL,"
					+ "credit_card INTEGER)";
			stmt.executeUpdate(sql);
			
			
			sql = "CREATE TABLE Materials ("
					+ "materials_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "supplier_id INTEGET NOT NULL,"
					+ "name TEXT,"
					+ "FOREIGN KEY (supplier_id) REFERENCES Suppliers(supplier_id) ON DELETE CASCADE)";
			stmt.executeUpdate(sql);

			
			sql = "CREATE TABLE Treatments ("
					+ "treatment_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT,"
					+ "description TEXT,"
					+ "price NUMERIC)";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE Appointments ("
					+ "appointment_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "patient_id INTEGER,"
					+ "treatment_id INTEGER,"
					+ "clinician_id INTEGER,"
					+ "date DATE,"
					+ "comments TEXT,"
					+ "FOREIGN KEY (patient_id) REFERENCES Patients(patient_id) ON DELETE CASCADE,"
					+ "FOREIGN KEY (treatment_id) REFERENCES Treatments(treatment_id) ON DELETE CASCADE,"
					+ "FOREIGN KEY (clinician_id) REFERENCES Clinicians(clinician_id) ON DELETE CASCADE)";
			stmt.executeUpdate(sql);
			
			
			sql = "CREATE TABLE Treatment_materials ("
					+ "treatment_id INTEGER,"
					+ "materials_id INTEGER,"
					+ "PRIMARY KEY (treatment_id, materials_id),"
					+ "FOREIGN KEY (treatment_id) REFERENCES Treatments(treatment_id) ON DELETE CASCADE,"
					+ "FOREIGN KEY (materials_id) REFERENCES Materials(materials_id) ON DELETE CASCADE)";
			stmt.executeUpdate(sql);	
			

			
			sql = "CREATE TABLE Suppliers (" 
					+ "supplier_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "supplierName TEXT,"
					+ "phone INTEGER,"
					+ "email TEXT UNIQUE NOT NULL)";
			stmt.executeUpdate(sql);

		
			sql = "INSERT INTO Patients (patient_id, name, surname, dob, phone, email, credit_card) "
					+ "VALUES (2341, 'Jorge', 'Gonzalez', '1992-06-23', '684027863', 'jorgegonzalez@gmail.com', '123456789')";
			stmt.executeUpdate(sql);

			
			sql = "INSERT INTO Clinicians (clinician_id, name, surname, specialty, phone, email) "
					+ "VALUES (7823, 'Julia', 'Velazquez', 'Surgery', '692438522', 'juliavelazquez@gmail.com')";
			stmt.executeUpdate(sql);


			sql = "INSERT INTO Treatments (treatment_id, name, description, price) "
					+ "VALUES (2003, 'Fenestration', 'Palate surgery', 200.00)";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO Treatment_materials (treatment_id, materials_id) "
					+ "VALUES (2003, 5053)";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO Suppliers (supplier_id, supplierName, phone, email) "
					+ "VALUES (8963, 'Roberto', '623985233', 'robertohernandez@gmail.com')";
			stmt.executeUpdate(sql);


			sql = "INSERT INTO Materials (materials_id, supplier_id, name)"
					+ "VALUES (4421, 8963,'Dental mirror')";
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
