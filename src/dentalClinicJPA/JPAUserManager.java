package dentalClinicJPA;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import dentalClinicPOJOS.Role;
import dentalClinicIFaces.UserManager;

public class JPAUserManager implements UserManager{
	
	private EntityManager entityManager;

	public JPAUserManager() {
		super();
		this.connect();
	}
	
	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
		entityManager = Persistence.createEntityManagerFactory("rehabclinic-provider").createEntityManager();
	
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("PRAGMA foreign_keys = ON").executeUpdate();
		entityManager.getTransaction().commit();
		
		if(this.getRoles().isEmpty())
		{
			Role patient = new Role("Patient");
			Role clinician = new Role("Clinician");
			this.newRole(patient);
			this.newRole(clinician);
		}
		
	}
	
	
	@Override
	public void disconnect() {
		entityManager.close();
	}
	
	@Override
	public void newRole(Role r) {
		
		entityManager.getTransaction().begin();
		entityManager.persist(r);
		entityManager.getTransaction().commit();
		
	}
	
	@Override
	public List<Role> getRoles(){
		
		Query q = entityManager.createNativeQuery("SELECT * FROM roles", Role.class);
		@SuppressWarnings("unchecked")
		List<Role> roles = (List<Role>) q.getResultList();
		
		return roles;
	};
	
	
	
	
}
