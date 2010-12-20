package oe.cav.web.design.column;

import com.rongji.webframework.struts.BaseForm;

public class ColumnForm extends BaseForm {

	private boolean useable;

	private String systemid;

	private String tip;// 用于说明扩展属性的作用

	/** identifier field */
	private String columncode;

	/** column ope prop */
	private String[] operole;

	/** nullable persistent field */
	private String formcode;

	/** persistent field */
	private String columnid;

	/** persistent field */
	private String columname;

	/** nullable persistent field */
	private String valuelist;

	/** persistent field */
	private String statusinfo;

	/** persistent field */
	private Long indexvalue;

	/** persistent field */
	private String musk;

	/** persistent field */
	private String opemode;

	/** persistent field */
	private String htmltype;

	/** nullable persistent field */
	private String checktype;

	/** persistent field */
	private String viewtype;

	/** nullable persistent field */
	private String extendattribute;

	private String[] selectname;

	public String[] getOperole() {
		return operole;
	}

	public void setOperole(String[] operole) {
		this.operole = operole;
	}

	public String[] getSelectname() {
		return selectname;
	}

	public void setSelectname(String[] selectname) {
		this.selectname = selectname;
	}

	public String getChecktype() {
		return checktype;
	}

	public void setChecktype(String checktype) {
		this.checktype = checktype;
	}

	public String getColumname() {
		return columname;
	}

	public void setColumname(String columname) {
		this.columname = columname;
	}

	// public String getColumncode() {
	// return columncode;
	// }
	//
	// public void setColumncode(String columncode) {
	// this.columncode = columncode;
	// }

	public String getColumnid() {
		return columnid;
	}

	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}

	public String getExtendattribute() {
		return extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String getFormcode() {
		return formcode;
	}

	public void setFormcode(String formcode) {
		this.formcode = formcode;
	}

	public String getHtmltype() {
		return htmltype;
	}

	public void setHtmltype(String htmltype) {
		this.htmltype = htmltype;
	}

	public Long getIndexvalue() {
		return indexvalue;
	}

	public void setIndexvalue(Long indexvalue) {
		this.indexvalue = indexvalue;
	}

	public String getMusk() {
		return musk;
	}

	public void setMusk(String musk) {
		this.musk = musk;
	}

	public String getOpemode() {
		return opemode;
	}

	public void setOpemode(String opemode) {
		this.opemode = opemode;
	}

	public String getStatusinfo() {
		return statusinfo;
	}

	public void setStatusinfo(String statusinfo) {
		this.statusinfo = statusinfo;
	}

	public String getValuelist() {
		return valuelist;
	}

	public void setValuelist(String valuelist) {
		this.valuelist = valuelist;
	}

	public String getViewtype() {
		return viewtype;
	}

	public void setViewtype(String viewtype) {
		this.viewtype = viewtype;
	}

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getColumncode() {
		return columncode;
	}

	public void setColumncode(String columncode) {
		this.columncode = columncode;
	}

	public boolean isUseable() {
		return useable;
	}

	public void setUseable(boolean useable) {
		this.useable = useable;
	}

}
