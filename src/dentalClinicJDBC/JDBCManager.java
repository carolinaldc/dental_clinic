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
			
			String sql = "CREATE TABLE IF NOT EXISTS Clinicians ("
					+ "clinician_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT,"
					+ "surname TEXT,"
					+ "specialty TEXT,"
					+ "phone TEXT,"
					+ "email TEXT)";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE IF NOT EXISTS Patients ("
					+ "patient_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT,"
					+ "surname TEXT,"
					+ "dob TEXT,"
					+ "phone TEXT,"
					+ "email TEXT,"
					+ "emergency TEXT,"
					+ "credit_card TEXT)";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE IF NOT EXISTS Materials ("
					+ "materials_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT,"
					+ "stock INTEGER)";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE IF NOT EXISTS Rooms ("
					+ "room_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "status TEXT)";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE IF NOT EXISTS Treatments ("
					+ "treatment_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT,"
					+ "description TEXT,"
					+ "price NUMERIC,"
					+ "room_id INTEGER REFERENCES Rooms(room_id) NOT NULL)";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE IF NOT EXISTS Clinicians_treatments ("
					+ "clinician_appointment_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "treatment_id INTEGER REFERENCES Treatments(treatment_id) NOT NULL,"
					+ "clinician_id INTEGER REFERENCES Clinicians(clinician_id) NOT NULL)";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE IF NOT EXISTS Patient_treatments ("
					+ "Patient_appointment_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "patient_id INTEGER REFERENCES Patients(patient_id) NOT NULL,"
					+ "treatment_id INTEGER REFERENCES Treatments(treatment_id) NOT NULL,"
					+ "date TEXT,"
					+ "comments TEXT)";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE IF NOT EXISTS Patients_clinicians ("
					+ "patient_id INTEGER REFERENCES Patients(patient_id) NOT NULL,"
					+ "clinician_id INTEGER REFERENCES Clinicians(clinician_id) NOT NULL,"
					+ "date TEXT,"
					+ "visit_info TEXT,"
					+ "PRIMARY KEY(patient_id, clinician_id))";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE IF NOT EXISTS Payments ("
					+ "payment_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "amount NUMERIC,"
					+ "payment_date TEXT,"
					+ "payment_method TEXT,"
					+ "status TEXT)";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE IF NOT EXISTS Treatment_materials ("
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "treatment_id INTEGER REFERENCES Treatments(treatment_id) NOT NULL,"
					+ "materials_id INTEGER REFERENCES Materials(materials_id) NOT NULL,"
					+ "description TEXT,"
					+ "tools TEXT)";
			stmt.executeUpdate(sql);	
			
			sql = "CREATE TABLE IF NOT EXISTS Suppliers (" 
					+ "supplier_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT,"
					+ "surname TEXT,"
					+ "phone TEXT,"
					+ "email TEXT,"
					+ "materials_id INTEGER REFERENCES Materials(materials_id) NOT NULL)";
			stmt.executeUpdate(sql);

		
			sql = "INSERT INTO Patients (patient_id, name, surname, dob, phone, email, emergency, credit_card) "
					+ "VALUES (2341, 'Jorge', 'Gonzalez', '1992-06-23', '+34684027863', 'jorgegonzalez@gmail.com', 'MEDIUM', '5223935698626682')";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO Clinicians (clinician_id, name, surname, specialty, phone, email) "
					+ "VALUES (7823, 'Julia', 'Velazquez', 'Surgery', '+34692438522', 'juliavelazquez@gmail.com')";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO Rooms (room_id, status) "
					+ "VALUES (2823, 'AVAILABLE')";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO Treatments (treatment_id, name, description, price, room_id) "
					+ "VALUES (2003, 'Fenestration', 'Palate surgery', 200.00, 2823)";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO Treatment_materials (treatment_id, materials_id, description, tools) "
					+ "VALUES (2003, 5053, 'Used to reach certain spots in the mouth', 'Dental mirror')";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO Suppliers (supplier_id, name, surname, phone, email, materials_id) "
					+ "VALUES (8963, 'Roberto', 'Hernandez', '+34623985233', 'robertohernandez@gmail.com', 5053)";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO Payments (payment_id, amount, payment_date, payment_method, status) "
					+ "VALUES (9823, 200.00, '2025-02-05', 'CARD', 'PENDING')";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO Patients_clinicians (patient_id, clinician_id, date, visit_info) "
					+ "VALUES (2341, 7823, '2025-02-05', 'Fenestration')";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO Clinicians_treatments (clinician_appointment_id, treatment_id, clinician_id) "
					+ "VALUES (8923, 2003, 7823)";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO Materials (materials_id, name, stock) "
					+ "VALUES (4421, 'Dental mirror', 160)";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO Patient_treatments (Patient_appointment_id, patient_id, treatment_id, date, comments) "
					+ "VALUES (9873, 2341, 2003, '2025-02-05', 'Palate surgery')";
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
