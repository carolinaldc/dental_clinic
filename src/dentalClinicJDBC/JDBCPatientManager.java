package dentalClinicJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.PatientManager;
import dentalClinicPOJOS.Patient;

public class JDBCPatientManager implements PatientManager {
    private Connection connection;

    public JDBCPatientManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addPatient(Patient p) {
        String sql = "INSERT INTO patients (name, dni, phone, birth_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getDni());
            ps.setString(3, p.getPhone());
            ps.setDate(4, new java.sql.Date(p.getBirthDate().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Patient getPatientById(int id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Patient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("dni"),
                    rs.getString("phone"),
                    rs.getDate("birth_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Patient> listPatients() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Patient p = new Patient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("dni"),
                    rs.getString("phone"),
                    rs.getDate("birth_date")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deletePatient(int id) {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePatient(Patient p) {
        String sql = "UPDATE patients SET name = ?, dni = ?, phone = ?, birth_date = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getDni());
            ps.setString(3, p.getPhone());
            ps.setDate(4, new java.sql.Date(p.getBirthDate().getTime()));
            ps.setInt(5, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Patient getPatient(String email) {
        String sql = "SELECT * FROM patients WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Patient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("dni"),
                    rs.getString("phone"),
                    rs.getDate("birth_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
