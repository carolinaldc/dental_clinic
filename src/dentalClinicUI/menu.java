package dentalClinicUI;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import dentalClinicJDBC.JDBCManager;
import dentalClinicJDBC.JDBCClinicianManager;
import dentalClinicJDBC.JDBCPatientManager;
import dentalClinicPOJOS.Patient;

public class menu {

	private static JDBCManager jdbcManager;
	private JDBCClinicianManager clinicianManager;
    	private JDBCPatientManager patientManager;
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
		
		jdbcManager = new JDBCManager();
		int option = 0 ; 
		try {
            do {
                System.out.println("\n=== Dental Clinic ===");
                System.out.println("1. Login");
                System.out.println("2. Sign-up");
                System.out.println("0. Exit");
                System.out.print("Choose an option: "); 

		switch (option){
				
	}
	

}
