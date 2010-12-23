package oe.bi.dataModel.obj;

import java.io.Serializable;

/**
 * 数据集对象(内部类)
 * 
 * @version 2006-06-23
 * @author chen.jia.xun(Robanco)
 * 
 */
public class DataSet implements Serializable{

	private String id;

	private String name;

	private String filtcondition;

	private String extendattribute;
	
	private String description;

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

	public String getFiltcondition() {
		return filtcondition;
	}

	public void setFiltcondition(String filtcondition) {
		this.filtcondition = filtcondition;
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

}
