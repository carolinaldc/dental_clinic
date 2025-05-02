package dentalClinicIFaces;

import java.util.List;

import dentalClinicPOJOS.Role;

public interface UserManager {
	
	void connect();
	void disconnect();
	List<Role> getRoles();
	void newRole(Role r);

}
