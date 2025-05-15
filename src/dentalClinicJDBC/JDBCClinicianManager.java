package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.ClinicianManager;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Patient;

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

    

	//()@Override
	//public void getClinician(String email) {
		// TODO Auto-generated method stub
		
	//}
	
	 //He creado este metodo para hacer lo de katerina de XML
	@Override
    public Clinician getClinicianByid(int id) {
    	
		Clinician clinician = null;
    	try {
    		Statement stmt = manager.getConnection().createStatement();
    		String sql = "SELECT * FROM Clinician WHERE id=" + id;
			
			ResultSet rs= stmt.executeQuery(sql);
			
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			Integer phone = rs.getInt("telephone");
			String email = rs.getString("email");
			String specialty = rs.getString("specialty");
            
			
			rs.close();
			stmt.close();
			
			clinician = new Clinician(name, surname, specialty, email, phone);
    	}catch(Exception e) 
		{
			e.printStackTrace();
		}
    	return clinician;
    }
	
	@Override 
	public Clinician getClinicianByEmail(String email) {
		Clinician clinician = null;
    	try {
    		Statement stmt = manager.getConnection().createStatement();
    		String sql = "SELECT * FROM Clinician WHERE email =" + email;
			
			ResultSet rs= stmt.executeQuery(sql);
			
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			Integer phone = rs.getInt("telephone");
			//Integer clinician_id = rs.getInt("clinician_id");
			String specialty = rs.getString("specialty");
            
			
			rs.close();
			stmt.close();
			
			clinician = new Clinician(name, surname, specialty, email, phone);
    	}catch(Exception e) 
		{
			e.printStackTrace();
		}
    	return clinician;
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


