package dentalClinicIFaces;

import java.util.List;

import dentalClinicPOJOS.Payment;

public interface PaymentManager {
	void addPayment(Payment payment);

    List<Payment> getAllPayments();

    void deletePayment(int paymentId);

    void updatePayment(Payment payment);
}
