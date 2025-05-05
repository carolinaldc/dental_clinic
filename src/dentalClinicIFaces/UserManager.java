package dentalClinicIFaces;

import java.util.List;

import dentalClinicPOJOS.Role;
import dentalClinicPOJOS.User;

public interface UserManager {
	
	void connect();
	void disconnect();
	List<Role> getRoles();
	void newRole(Role r);
	void newUser(User user);
	Role getRole(Integer id);
	User checkPassword(String mail, String password);

}
