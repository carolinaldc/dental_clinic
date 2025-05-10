package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.ClinicianManager;
import dentalClinicPOJOS.Clinician;

public class JDBCClinicianManager implements ClinicianManager {

    private JDBCManager manager;

    public JDBCClinicianManager(JDBCManager manager) {
        this.manager = manager;
    }

    @Override
    public void addClinician(Clinician clinician) {
        String sql = "INSERT INTO clinicians (name, specialty) VALUES (?, ?)";

        try (PreparedStatement stmt = manager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, clinician.getName());
            stmt.setString(2, clinician.getSpeciality());
            stmt.executeUpdate();
            System.out.println("Dentista añadido correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al añadir dentista: " + e.getMessage());
        }
    }

    @Override
    public List<Clinician> listClinicians() {
        List<Clinician> clinicians = new ArrayList<>();
        String sql = "SELECT * FROM clinicians";

        try (Statement stmt = manager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Clinician c = new Clinician(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specialty")
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

	
    @Override
    public Clinician getClinicianById(int id) {
        try (PreparedStatement ps = manager.getConnection().prepareStatement(
                "SELECT * FROM clinicians WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Clinician(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specialty")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

	
    @Override
    public void deleteClinician(int id) {
        try (PreparedStatement ps = manager.getConnection().prepareStatement(
                "DELETE FROM clinicians WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClinician(Clinician cl) {
        try (PreparedStatement ps = manager.getConnection().prepareStatement(
                "UPDATE clinicians SET name=?, specialty=? WHERE id=?")) {
            ps.setString(1, cl.getName());
            ps.setString(2, cl.getSpeciality());
            ps.setInt(3, cl.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
        
}


