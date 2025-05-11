package dentalClinicPOJOS;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Payment implements Serializable {

    private static final long serialVersionUID = -2637321372475755L;
    
    private Integer payment_id;
    private Double amount;
    private Date payment_date;
    private Method payment_method;
    private Status status;
    private Patient patient;

    public enum Method {
        CASH, CARD, BIZUM
    }

    public enum Status {
        PENDING, COMPLETED, CANCELLED
    }

    public Payment(Double amount, Date date, Method method, Status status, Patient patient) {
        super();
    	this.amount = amount;
    	this.payment_date = date;
    	this.payment_method = method;
    	this.status = status;
    	this.patient = patient; 
    }

 
	public int getId() {
        return payment_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return payment_date;
    }

    public void setDate(Date date) {
        this.payment_date = date;
    }

    public Method getMethod() {
        return payment_method;
    }

    public void setMethod(Method method) {
        this.payment_method = method;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Patient getPatient_id() {
        return patient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(payment_id, amount, payment_date, payment_method, status, patient);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Payment))
            return false;
        Payment other = (Payment) obj;
        return payment_id == other.payment_id &&
               Double.compare(other.amount, amount) == 0 &&
               patient == other.patient &&
               Objects.equals(payment_date, other.payment_date) &&
               payment_method == other.payment_method &&
               status == other.status;
    }

    @Override
    public String toString() {
        return "Payment [id=" + payment_id + ", amount=" + amount + ", date=" + payment_date +
               ", method=" + payment_method + ", status=" + status + ", patient_id=" + patient + "]";
    }
}
