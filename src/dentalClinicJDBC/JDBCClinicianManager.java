package dentalClinicJDBC;

import java.sql.* ; 
import java.util.ArrayList ; 
import java.util.List ;

import dentalClinicIFaces.ClinicianManager;
import dentalClinicPOJOS.Clinician; 

public class JDBCClinicianManager implements ClinicianManager {
    
    private Connection connection;

    public JDBCClinicianManager(Connection connection) {
        this.connection = connection;
    }

    // Añadir un clinician
    public void addClinician(Clinician clinician) {
        String sql = "INSERT INTO Clinician (id, name, specialty, email) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clinician.getId());
            stmt.setString(2, clinician.getName());
            stmt.setString(3, clinician.getSpeciality().toString());
            stmt.setString(4, clinician.getEmail());

            stmt.executeUpdate();
            System.out.println("Dentista añadido correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al añadir dentista: " + e.getMessage());
        }
    }

    // Mostrar todos los clinicians
    public List<Clinician> getAllClinicians() {
        List<Clinician> clinicians = new ArrayList<>();
        String sql = "SELECT * FROM Clinician";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Clinician c = new Clinician(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("specialty"),
                    rs.getString("email"),
                    rs.getInt("phone")
                );
                clinicians.add(c);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los dentistas: " + e.getMessage());
        }

        return clinicians;
    }

	@Override
	public void getClinician(String email) {
		// TODO Auto-generated method stub
		
	}
}
