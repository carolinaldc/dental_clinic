package dentalClinicXML;

import dentalClinicJDBC.JDBCClinicianManager;
import dentalClinicJDBC.JDBCManager;
import dentalClinicJDBC.JDBCPatientManager;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Patient;

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
		JDBCClinicianManager jdbcClinicianManager = new JDBCClinicianManager(manager);
		try {
			
			
	        Patient patient = jdbcPatientManager.getPatientByid(id);
	        //Preguntar a Katerina si esto esta bien 
	        Clinician clinician = jdbcClinicianManager.getClinicianByid(id);
	        patient.setClinician(clinician);
	        
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
	public Clinician xml2Clinician(File xml) {
		
		Clinician clinician = null;
		try {
			//read clinician from xml file
			JAXBContext jaxbContext = JAXBContext.newInstance(Clinician.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			clinician = (Clinician) unmarshaller.unmarshal(xml);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return clinician;
		
	}

}
