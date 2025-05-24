package dentalClinicXML;

import dentalClinicJDBC.JDBCClinicianManager;
import dentalClinicJDBC.JDBCManager;
import dentalClinicJDBC.JDBCMaterialManager;
import dentalClinicPOJOS.Clinician;
import dentalClinicPOJOS.Material;


import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import dentalClinicIFaces.XMLManager ;

public class XMLManagerImpl implements XMLManager {
	
	JDBCManager manager;
	
	@Override
	public void clinician2xml(Integer id) {
		 
		manager = new JDBCManager();
		JDBCClinicianManager jdbcClinicianManager = new JDBCClinicianManager(manager); 
		
		try {
			
			
	        Clinician clinician = jdbcClinicianManager.getClinicianById(id);
	        
	        //export this Clinician to a XML file
	        JAXBContext jaxbcontext =  JAXBContext.newInstance(Clinician.class);
			Marshaller marshaller = jaxbcontext.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);

			File file = new File("./xmls/Clinician.xml");
			
			marshaller.marshal(clinician,file);
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public Clinician xml2Clinician(File xml) {
		
		Clinician clinician = null;
		try {
			//read Clinician from xml file
			JAXBContext jaxbContext = JAXBContext.newInstance(Clinician.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			clinician = (Clinician) unmarshaller.unmarshal(xml);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return clinician;
		
	}
	
	@Override
	public void material2xml(Integer id) {
		 
		manager = new JDBCManager();
		JDBCMaterialManager jdbcMaterialManager = new JDBCMaterialManager(manager); 
		
		try {
			
			
	        Material material = jdbcMaterialManager.getMaterialByid(id);
	        
	        //export this Material to a XML file
	        JAXBContext jaxbcontext =  JAXBContext.newInstance(Material.class);
			Marshaller marshaller = jaxbcontext.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);

			File file = new File("./xmls/Material.xml");
			
			marshaller.marshal(material,file);
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Material xml2Material(File xml) {
		
		Material material = null;
		try {
			//read Material from xml file
			JAXBContext jaxbContext = JAXBContext.newInstance(Material.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			material = (Material) unmarshaller.unmarshal(xml);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return material;
		
	}

}
