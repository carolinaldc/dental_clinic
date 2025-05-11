package dentalClinicJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.PaymentManager;
import dentalClinicPOJOS.Payment;
import dentalClinicPOJOS.PatientTreatment;

public class JDBCPaymentManager implements PaymentManager {
	private Connection c;
    private ConnectionManager conMan;

    public JDBCPaymentManager(ConnectionManager connectionManager) {
        this.setConMan(connectionManager);
        this.c = connectionManager.getConnection();
    }
    
    @Override
    public void addPayment(Payment payment) {
        try {
            String sql = "INSERT INTO payments (amount, date, treatment_id) VALUES (?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setDouble(1, payment.getAmount());
            ps.setDate(2, payment.getDate());
            ps.setInt(3, payment.getTreatment().getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error inserting payment");
            e.printStackTrace();
        }
    }

    @Override
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        try {
            String sql = "SELECT * FROM payments";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("payment_id");
                double amount = rs.getDouble("amount");
                java.sql.Date date = rs.getDate("date");
                int treatmentId = rs.getInt("treatment_id");

                PatientTreatment treatment = conMan.getTreatmentManager().getTreatmentById(treatmentId); // suponiendo que tienes este método

                Payment payment = new Payment(id, amount, date, treatment);
                payments.add(payment);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving payments");
            e.printStackTrace();
        }

        return payments;
    }

    @Override
    public void deletePayment(int paymentId) {
        try {
            String sql = "DELETE FROM payments WHERE payment_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, paymentId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error deleting payment");
            e.printStackTrace();
        }
    }

    @Override
    public void updatePayment(Payment payment) {
        try {
            String sql = "UPDATE payments SET amount = ?, date = ?, treatment_id = ? WHERE payment_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setDouble(1, payment.getAmount());
            ps.setDate(2, payment.getDate());
            ps.setInt(3, payment.getTreatment().getId());
            ps.setInt(4, payment.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error updating payment");
            e.printStackTrace();
        }
    }

    public ConnectionManager getConMan() {
        return conMan;
    }

    public void setConMan(ConnectionManager conMan) {
        this.conMan = conMan;
    }
}

