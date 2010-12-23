package oe.bi.dataModel.obj;

import java.io.Serializable;

/**
 * 
 * @version 2006-06-23
 * @author chen.jia.xun (Robanco)
 * 
 */
public class TargetColumn implements Serializable {
	private String id;

	private String sqltype;

	private String name;

	private String alarm;

	private String extendattribute;

	private String description;

	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public String getSqltype() {
		return sqltype;
	}

	public void setSqltype(String sqltype) {
		this.sqltype = sqltype;
	}

}
