package dentalClinicXML;

import dentalClinicJDBC.JDBCManager;
import dentalClinicJDBC.JDBCPatientManager;
import dentalClinicPOJOS.Patient;
import dentalClinicPOJOS.Patients_Clinician;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import dentalClinicIFaces.XMLManager ;

public class XMLManagerImpl implements XMLManager {
	
	JDBCManager manager;
	
	@Override
	public void patient2xml(Integer id) {
		 
		manager = new JDBCManager();
		JDBCPatientManager jdbcPatientManager = new JDBCPatientManager(manager); 
		
		try {
			
			
	        Patient patient = jdbcPatientManager.getPatientByid(id);
	        
	      //export this Patient to a XML file
	        JAXBContext jaxbcontext =  JAXBContext.newInstance(Patient.class);
			Marshaller marshaller = jaxbcontext.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);

			File file = new File("./xmls/Patient.xml");
			
			marshaller.marshal(patient,file);
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		
	}

	@Override
	public Patients_Clinician xml2PatientsClinician(File xml) {
		
		Patients_Clinician patientsClinician = null;
		try {
			//read Patients_Clinician from xml file
			JAXBContext jaxbContext = JAXBContext.newInstance(Patients_Clinician.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			patientsClinician = (Patients_Clinician) unmarshaller.unmarshal(xml);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return patientsClinician;
		
	}

}
