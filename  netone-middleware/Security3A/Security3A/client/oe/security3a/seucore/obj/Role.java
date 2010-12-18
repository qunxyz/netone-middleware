package oe.security3a.seucore.obj;


public class Role extends Entity {
	private User[] assignedUserRoles;

	private Permission[] permissions;

	private Application ownerApplication;

	/**
	 * size =1 µ¥¼Ì³Ð
	 */
	private Role[] extendsRoles;

	private boolean active;

	public Role() {
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public User[] getAssignedUserRoles() {
		return assignedUserRoles;
	}

	public void setAssignedUserRoles(User[] assignedUserRoles) {
		this.assignedUserRoles = assignedUserRoles;
	}

	public Application getOwnerApplication() {
		return ownerApplication;
	}

	public void setOwnerApplication(Application ownerApplication) {
		this.ownerApplication = ownerApplication;
	}

	public Permission[] getPermissions() {
		return permissions;
	}

	public void setPermissions(Permission[] permissions) {
		this.permissions = permissions;
	}

	public Role[] getExtendsRoles() {
		return extendsRoles;
	}

	public void setExtendsRoles(Role[] extendsRoles) {
		this.extendsRoles = extendsRoles;
	}

}
