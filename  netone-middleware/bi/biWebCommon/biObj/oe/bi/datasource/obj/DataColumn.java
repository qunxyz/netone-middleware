package oe.bi.datasource.obj;

import java.io.Serializable;

/**
 * ���ݼ���Ԫ�ض���
 * 
 * @author ibm
 * 
 */
public class DataColumn implements Serializable{
	/** Ԫ��ID */
	private String columnid;

	/** Ԫ���� */
	private String columnname;

	/** Ԫ�����ͣ�Number,char,varchar,boolean......�� */
	private String types;

	/** Ԫ������ */
	private String description;

	/** Ԫ����չ���� */
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
