package oe.bi.dataModel.obj;

import java.io.Serializable;

/**
 * 
 * @version 2006-06-23
 * @author chen.jia.xun (Robanco)
 * 
 */
public class Linker implements Serializable{

	String id;

	String name;

	String formDataSet;

	String toDataSet;

	String description;

	String extendattribute;

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

	public String getFormDataSet() {
		return formDataSet;
	}

	public void setFormDataSet(String formDataSet) {
		this.formDataSet = formDataSet;
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

	public String getToDataSet() {
		return toDataSet;
	}

	public void setToDataSet(String toDataSet) {
		this.toDataSet = toDataSet;
	}
}
