package oe.bi.datasource.obj;

import java.io.Serializable;

/**
 * 数据集的元素对象
 * 
 * @author ibm
 * 
 */
public class DataColumn implements Serializable{
	/** 元素ID */
	private String columnid;

	/** 元素名 */
	private String columnname;

	/** 元素类型（Number,char,varchar,boolean......） */
	private String types;

	/** 元素描述 */
	private String description;

	/** 元素扩展属性 */
	private String extendAttribute;

	public String getColumnid() {
		return columnid;
	}

	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}

	public String getColumnname() {
		return columnname;
	}

	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExtendAttribute() {
		return extendAttribute;
	}

	public void setExtendAttribute(String extendAttribute) {
		this.extendAttribute = extendAttribute;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

}
