package oe.cav.bean.logic.tools;

import java.io.Serializable;
import java.util.List;

import oe.cav.bean.logic.form.TCsForm;

public class DyObj implements Serializable{
	/**
	 * 系统ID,可以是数据库名,或者逻辑上的一个系统标识
	 */
	private String systemid;

	/**
	 * 表单集合,其中key是formcode,value是TCsForm
	 */
	private TCsForm from;

	/**
	 * 字段集合TCsColumn
	 */
	private List column;


	private String ExtendAttribute;


	public List getColumn() {
		return column;
	}


	public void setColumn(List column) {
		this.column = column;
	}


	public String getExtendAttribute() {
		return ExtendAttribute;
	}


	public void setExtendAttribute(String extendAttribute) {
		ExtendAttribute = extendAttribute;
	}


	public TCsForm getFrom() {
		return from;
	}


	public void setFrom(TCsForm from) {
		this.from = from;
	}


	public String getSystemid() {
		return systemid;
	}


	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

}
