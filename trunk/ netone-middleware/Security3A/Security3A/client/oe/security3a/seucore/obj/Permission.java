package oe.security3a.seucore.obj;

public class Permission extends Entity {

	private ProtectedObject protectedObject;

	private Operation operation;

	private boolean active;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public ProtectedObject getProtectedObject() {
		return protectedObject;
	}

	public void setProtectedObject(ProtectedObject protectedObject) {
		this.protectedObject = protectedObject;
	}

}
