package oe.bi.etl.obj;

import java.io.Serializable;

/**
 * 指标元素
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class TargetElement implements Serializable{
	/** 指标ID */
	private String id;

	private String name;
	
	private String nativeid;

	private String type;

	private String extendattribute;

	public String getExtendattribute() {
		return extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNativeid() {
		return nativeid;
	}

	public void setNativeid(String nativeid) {
		this.nativeid = nativeid;
	}

}
