package dentalClinicIFaces;

import java.util.List;

import dentalClinicPOJOS.Role;
import dentalClinicPOJOS.User;

public interface UserManager {
	
	void connect();
	void disconnect();
	List<Role> getRoles();
	void newRole(Role r);
	boolean newUser(User user);
	Role getRole(Integer id);
	User checkPassword(String mail, String password);
	void deleteUser(User u);
	User getUserByEmail(String email);
	void changeEmail(User u, String new_email);
	void changePassword(User u, String new_passwd);

}
