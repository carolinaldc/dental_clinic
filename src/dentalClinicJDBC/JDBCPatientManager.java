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
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO patients (name, dni, phone, birth_date) VALUES (?, ?, ?, ?)")) {
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
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM patients WHERE id = ?")) {
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
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM patients");
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
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM patients WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePatient(Patient p) {
        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE patients SET name=?, dni=?, phone=?, birth_date=? WHERE id=?")) {
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
}
