package dentalClinicPOJOS;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Payment implements Serializable {

    private static final long serialVersionUID = -2637321372475755L;
    
    private Integer id;
    private Double amount;
    private Date date;
    private Method method;
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
    	this.date = date;
    	this.method = method;
    	this.status = status;
    	this.patient = patient; 
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
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
        return Objects.hash(id, amount, date, method, status, patient);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Payment))
            return false;
        Payment other = (Payment) obj;
        return id == other.id &&
               Double.compare(other.amount, amount) == 0 &&
               patient == other.patient &&
               Objects.equals(date, other.date) &&
               method == other.method &&
               status == other.status;
    }

    @Override
    public String toString() {
        return "Payment [id=" + id + ", amount=" + amount + ", date=" + date +
               ", method=" + method + ", status=" + status + ", patient_id=" + patient + "]";
    }
}
