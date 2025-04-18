package dentalClinicJDBC;

import java.sql.* ; 
import java.util.ArrayList ; 
import java.util.List ; 
import java.sql.Connection;

public class JDBCClinicianManager {
	
	 private Connection connection;

	    public JDBCClinicianManager(Connection connection) {
	        this.connection = connection;
	    }
	    
	  public void addClinician(Clinician clinician) {
	        String sql = "INSERT INTO Clinician (id, name, specialty, email) VALUES (?, ?, ?, ?)";

	        
	       //aqui lo que hago es mostrar todos los clinicians 
	        public List<Clinician> getAllClinicians() {
	            List<Clinician> clinicians = new ArrayList<>();
	            String sql = "SELECT * FROM Clinician";

	            try (Statement stmt = connection.createStatement();
	                 ResultSet rs = stmt.executeQuery(sql)) {

	                while (rs.next()) {
	                    Clinician c = new Clinician(
	                        rs.getInt("id"),
	                        rs.getString("name"),
	                        rs.getString("specialty"),
	                        rs.getString("email")
	                    );
	                    clinicians.add(c);
	                }

	            } catch (SQLException e) {
	                System.err.println("Error al obtener los dentistas: " + e.getMessage());
	            }

	            return clinicians;
	        }
	    }
	  
	  //añadir un clinician 
	  
	  public void addClinician(Clinician clinician) {
	        String sql = "INSERT INTO Clinician (id, name, specialty, email) VALUES (?, ?, ?, ?)";

	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setInt(1, clinician.getId());
	            stmt.setString(2, clinician.getName());
	            stmt.setString(3, clinician.getSpecialty());
	            stmt.setString(4, clinician.getEmail());

	            stmt.executeUpdate();
	            System.out.println("Dentista añadido correctamente.");
	        } catch (SQLException e) {
	            System.err.println("Error al añadir dentista: " + e.getMessage());
	        }
	    }
}
	  
