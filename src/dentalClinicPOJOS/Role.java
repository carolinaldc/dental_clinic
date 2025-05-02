package dentalClinicPOJOS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;




public class Role implements Serializable{

	
	private static final long serialVersionUID = -2575051255914974779L;
	
	@Id
	@GeneratedValue(generator = "roles")
	@TableGenerator(name= "roles", table = "sqlite_sequence", pkColumnName = "name", valueColumnName= "seq", pkColumnValue = "roles")
	private Integer id;
	@Column(name="description")
	private String description;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	private List<User> users;
	
	public Role() {
		super();
		users = new ArrayList<User>();
	}
	
	public Role(String description) {
		super();
		this.description = description;
		users = new ArrayList<User>();

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, users);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(users, other.users);
	}

	@Override
	public String toString() {
		return "Role [description=" + description + ", users=" + users + "]";
	}
	
	
	
	
	
	
	
	

}
