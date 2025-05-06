package dentalClinicUI;

import dentalClinicJDBC.JDBCManager;
import dentalClinicJDBC.JDBCClinicianManager;
import dentalClinicJDBC.JDBCPatientManager;

public class menu {

	private static JDBCManager jdbcManager;
    private JDBCClinicianManager clinicianManager;
    private JDBCPatientManager patientManager;
    private static UserManager userManager;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello world");
		
		jdbcManager = new JDBCManager();
		patientManager = new JDBCPatientManager(jdbcManager);
        clinicianManager = new JDBCClinicianManager(jdbcManager);
		
        
        try {
            do {
                System.out.println("\n=== Dental Clinic ===");
                System.out.println("1. Login");
                System.out.println("2. Sign-up");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
	}
	

}
