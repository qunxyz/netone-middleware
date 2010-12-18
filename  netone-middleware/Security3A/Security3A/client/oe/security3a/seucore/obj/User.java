package oe.security3a.seucore.obj;


public class User extends Entity {
	/**
	 * √‹¬Î
	 */
	private String password;

	private Role[] roles;

	private boolean active;
	

	public String getPassword() {
		return password;
	}

	public boolean isActive() {
		return active;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role[] getRoles() {
		return roles;
	}

	public void setRoles(Role[] roles) {
		this.roles = roles;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (getId() == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User user = (User) obj;
		return this.getId().equals(user.getId());
	}

	public int hashCode() {
		return this.getId().intValue();
	}
}
