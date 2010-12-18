package oe.security3a.seupublic.authentication.obj.core;

import java.io.OutputStream;
import java.io.Serializable;

import org.w3c.dom.Node;

/**
 *  Ù–‘‘™Àÿ
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class AttributeCtx implements Serializable{

	public AttributeCtx(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public AttributeCtx(String name, String value, String description) {
		this.name = name;
		this.value = value;
		this.description = description;
	}

	private String name;

	private String value;

	private String description;

	public static AttributeCtx getInstance(Node root) {
		throw new RuntimeException("UnImplement");
	}

	public void encode(OutputStream output) {
		throw new RuntimeException("UnImplement");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
