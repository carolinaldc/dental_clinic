package dentalClinicJDBC;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dentalClinicIFaces.PaymentManager;
import dentalClinicPOJOS.Payment;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.PatientTreatment;

public class JDBCPaymentManager implements PaymentManager {
	//private Connection c;
    private JDBCManager conMan;

    public JDBCPaymentManager(JDBCManager connectionManager) {
        this.conMan = connectionManager;
        //this.c = connectionManager.getConnection();
    }
    
    @Override
    public void addPayment(Payment payment) {
        try {
            String sql = "INSERT INTO payments (payment_id, amount, payment_date, payment_method,status) VALUES (?, ?, ?)";
            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
            
            ps.setDouble(1, payment.getAmount());
            ps.setDate(2, new java.sql.Date(payment.getDate().getTime()));  // Conversión de Date
            ps.setString(3, payment.getMethod().toString());  // Convertir Payment.Method a String
            ps.setString(4, payment.getStatus().toString());
            
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
        JDBCPatientManager jdbcPatientsCliniciansManager = new JDBCPatientManager(conMan);
        try {
            String sql = "SELECT * FROM payments";
            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("payment_id");
                Double amount = rs.getDouble("amount");
                Date date = rs.getDate("date");
                Integer treatmentId = rs.getInt("treatment_id");
                
                String method = rs.getString("payment_method");
                String status = rs.getString("status");
                
                Integer patient_Id = rs.getInt("patient_id");
                Patient patient = jdbcPatientsCliniciansManager.getPatientByid(patient_Id);
                

                //Preguntar a katerina como pasar Objeto a SQL y si lo de los enumerados esta bien
                Payment payment = new Payment(amount, date, method ,status , patient);
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
            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
            ps.setInt(1, paymentId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error deleting payment");
            e.printStackTrace();
        }
    }

    //TODO ns si esto esta bien
    @Override
    public void updatePayment(Payment payment) {
        try {
            String sql = "UPDATE payments SET amount = ?, date = ?, treatment_id = ? WHERE payment_id = ?";
            PreparedStatement ps = conMan.getConnection().prepareStatement(sql);
            ps.setDouble(1, payment.getAmount());
            ps.setDate(2, payment.getDate());
            //ps.setInt(3, payment.getTreatment().getId());
            ps.setInt(4, payment.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error updating payment");
            e.printStackTrace();
        }
    }

}

