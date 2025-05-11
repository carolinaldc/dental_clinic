package dentalClinicJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import dentalClinicIFaces.*; 

public class ConnectionManager {
	
	private Connection c;
    private ClinicianManager clinicianMan;
    private PatientManager patientMan;
    private MaterialManager materialMan;
    private PaymentManager paymentMan;
    private UserManager userMan;
    private TreatmentManager treatmentMan;
    private RoomManager roomMan;
    private SupplierManager supplierMan;
    private XMLManager xmlMan;

    
    


	public ConnectionManager() {
		
		this.connect();
		this.clinicianMan = new JDBCClinicianManager(this);
		this.patientMan = new JDBCPatientManager(this);
		this.materialMan = new JDBCMaterialManager(this);
		this.paymentMan = new JDBCPaymentManager(this);
		this.userMan = new JDBCUserManager(this);
		this.treatmentMan = new JDBCTreatmentManager(this);
		this.roomMan = new JDBCRoomManager(this);
		this.supplierMan = new JDBCSupplierManager (this);
		this.xmlMan = new JDBCXMLManager();
		this.createTables(); 
	}
    
	public Connection getConnection() {
        return c;
    }

	    public ClinicianManager getClinicianMan() {
	        return clinicianMan;
	    }

	    public PatientManager getPatientMan() {
	        return patientMan;
	    }

	    public MaterialManager getMaterialMan() {
	        return materialMan;
	    }

	    public PaymentManager getPaymentMan() {
	        return paymentMan;
	    }

	    public UserManager getUserMan() {
	        return userMan;
	    }

	    public TreatmentManager getTreatmentMan() {
	        return treatmentMan;
	    }

	    public RoomManager getRoomMan() {
	        return roomMan;
	    }

	    public SupplierManager getSupplierMan() {
	        return supplierMan;
	    }

	    public XMLManager getXmlMan() {
	        return xmlMan;
	    }
	}
	}


