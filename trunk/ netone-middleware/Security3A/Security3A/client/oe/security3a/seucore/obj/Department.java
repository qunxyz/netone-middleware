/* Generated by Together */

package oe.security3a.seucore.obj;

public class Department extends Tree {

	private Role[] assignedRoles;

	private Clerk[] clerks;

	public Role[] getAssignedRoles() {
		return assignedRoles;
	}

	public void setAssignedRoles(Role[] assignedRoles) {
		this.assignedRoles = assignedRoles;
	}

	public Clerk[] getClerks() {
		return clerks;
	}

	public void setClerks(Clerk[] clerks) {
		this.clerks = clerks;
	}

}
