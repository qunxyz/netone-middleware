package oe.cav.bean.logic.tools;

import java.io.Serializable;
import java.util.List;

import oe.cav.bean.logic.form.TCsForm;

public class DyObj implements Serializable{
	/**
	 * ϵͳID,���������ݿ���,�����߼��ϵ�һ��ϵͳ��ʶ
	 */
	private String systemid;

	/**
	 * ������,����key��formcode,value��TCsForm
	 */
	private TCsForm from;

	/**
	 * �ֶμ���TCsColumn
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
