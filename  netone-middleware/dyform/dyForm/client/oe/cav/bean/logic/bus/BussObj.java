package oe.cav.bean.logic.bus;

import java.io.Serializable;
import java.util.Map;

public class BussObj implements Serializable {



	String systemid;

	String formcode;

	String[] primarkeys;

	Map columnIdValue;

	Map columnIdType;

	Map extendattribute;

	public Map getColumnIdType() {
		return columnIdType;
	}

	public void setColumnIdType(Map columnIdType) {
		this.columnIdType = columnIdType;
	}

	public Map getColumnIdValue() {
		return columnIdValue;
	}

	public void setColumnIdValue(Map columnIdValue) {
		this.columnIdValue = columnIdValue;
	}

	public Map getExtendattribute() {
		return extendattribute;
	}

	public void setExtendattribute(Map extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String getFormcode() {
		return formcode;
	}

	public void setFormcode(String formcode) {
		this.formcode = formcode;
	}

	public String[] getPrimarkeys() {
		return primarkeys;
	}

	public void setPrimarkeys(String[] primarkeys) {
		this.primarkeys = primarkeys;
	}

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}


}
