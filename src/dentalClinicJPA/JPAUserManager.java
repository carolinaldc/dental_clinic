package dentalClinicJPA;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import dentalClinicPOJOS.Role;
import dentalClinicPOJOS.User;
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
		
		entityManager = Persistence.createEntityManagerFactory("dentalclinic-provider").createEntityManager();
	
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("PRAGMA foreign_keys = ON").executeUpdate();
		entityManager.getTransaction().commit();
		
		if(this.getRoles().isEmpty())
		{
			Role patient = new Role("Patient");
			Role clinician = new Role("Clinician");
			Role supplier = new Role("Supplier");
			this.newRole(patient);
			this.newRole(clinician);
			this.newRole(supplier);
		}
		
	}
	
	
	@Override
	public void disconnect() {
		entityManager.close();
	}
	
	
	@Override
	public void newUser(User user) {
		
		entityManager.getTransaction().begin();
		entityManager.persist(user);
		entityManager.getTransaction().commit();
		
	}
	
	
	
	@Override
	public void newRole(Role role) {
		
		entityManager.getTransaction().begin();
		entityManager.persist(role);
		entityManager.getTransaction().commit();
		
	}
	
	@Override
	public Role getRole(Integer id) {
		
		Query query = entityManager.createNativeQuery("SELECT * FROM roles where id="+ id, Role.class);
		Role role = (Role) query.getSingleResult();
		
		return role;
	}
	
	@Override
	public List<Role> getRoles(){
		
		Query query = entityManager.createNativeQuery("SELECT * FROM roles", Role.class);
		@SuppressWarnings("unchecked")
		List<Role> roles = (List<Role>) query.getResultList();
		
		return roles;
	};
	
	@Override
	public User checkPassword(String mail, String password) {
		User user = null;
		Query query = entityManager.createNativeQuery("SELECT * FROM users where email= ? AND password = ?", User.class);
		query.setParameter(1, mail);
		try {
			MessageDigest message = MessageDigest.getInstance("MD5");
			message.update(password.getBytes());
			byte[] digest = message.digest();
			query.setParameter(2, digest);
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		try {
			user = (User) query.getSingleResult();
		}catch(NoResultException e) {}
		
		return user;
		
	}
}
