package oe.security3a.seupublic.authentication.obj.core;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.Set;

import org.w3c.dom.Node;

/**
 * ÊÚÈ¨ÔªËØ
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class PermissionCtx implements Serializable {

	private AttributeCtx resource;

	private AttributeCtx action;

	private AttributeCtx status;

	private Set<AttributeCtx> set;

	public static PermissionCtx getInstance(Node root) {
		throw new RuntimeException("UnImplement");
	}

	public void encode(OutputStream output) {
		throw new RuntimeException("UnImplement");
	}

	public AttributeCtx getAction() {
		return action;
	}

	public void setAction(AttributeCtx action) {
		this.action = action;
	}

	public AttributeCtx getResource() {
		return resource;
	}

	public void setResource(AttributeCtx resource) {
		this.resource = resource;
	}

	public AttributeCtx getStatus() {
		return status;
	}

	public void setStatus(AttributeCtx status) {
		this.status = status;
	}

	public Set<AttributeCtx> getSet() {
		return set;
	}

	public void setSet(Set<AttributeCtx> set) {
		this.set = set;
	}

}
