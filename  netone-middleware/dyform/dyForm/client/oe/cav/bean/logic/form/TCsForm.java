package oe.cav.bean.logic.form;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TCsForm implements Serializable {
	// 隶属维度
	private String belongx;

	// 特殊数据源
	private String sqlinfo = "*";

	/**
	 * 隶属维度的层次
	 */
	private String dimlevel;

	/**
	 * 时间维度的层次
	 */
	private String timelevel = "1h";

	/**
	 * 隶属维度的数据来源(资源树的OU)
	 */
	private String dimdata;

	/** identifier field */
	private String formcode;

	private String subform;

	/** nullable persistent field */
	private String systemid;

	/** nullable persistent field */
	private String formname;

	/** nullable persistent field */
	private String description;

	/** nullable persistent field */
	private String created;

	/** nullable persistent field */
	private String designer;

	/** nullable persistent field */
	private String statusinfo;

	/** nullable persistent field */
	private String extendattribute;

	/** nullable persistent field */
	private String typeinfo;

	private String orderinfo;

	private String butinfo;

	private String listinfo;

	private String viewbutinfo;

	private String participant;

	private String styleinfo;

	private String tablename;

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getDimlevel() {
		return dimlevel;
	}

	public void setDimlevel(String dimlevel) {
		this.dimlevel = dimlevel;
	}

	public String getTimelevel() {
		return timelevel;
	}

	public void setTimelevel(String timelevel) {
		this.timelevel = timelevel;
	}

	public String getButinfo() {
		return butinfo;
	}

	public void setButinfo(String butinfo) {
		this.butinfo = butinfo;
	}

	public String getListinfo() {
		return listinfo;
	}

	public void setListinfo(String listinfo) {
		this.listinfo = listinfo;
	}

	public String getOrderinfo() {
		return orderinfo;
	}

	public void setOrderinfo(String orderinfo) {
		this.orderinfo = orderinfo;
	}

	/** full constructor */
	public TCsForm(String systemid, String formname, String description,
			String created, String designer, String statusinfo,
			String extendattribute, String typeinfo) {
		this.systemid = systemid;
		this.formname = formname;
		this.description = description;
		this.created = created;
		this.designer = designer;
		this.statusinfo = statusinfo;
		this.extendattribute = extendattribute;
		this.typeinfo = typeinfo;
	}

	/** default constructor */
	public TCsForm() {
	}

	public String getFormcode() {
		return this.formcode;
	}

	public void setFormcode(String formcode) {
		this.formcode = formcode;
	}

	public String getSystemid() {
		return this.systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public String getFormname() {
		return this.formname;
	}

	public void setFormname(String formname) {
		this.formname = formname;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreated() {
		return this.created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getDesigner() {
		return this.designer;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}

	public String getStatusinfo() {
		return this.statusinfo;
	}

	public void setStatusinfo(String statusinfo) {
		this.statusinfo = statusinfo;
	}

	public String getExtendattribute() {
		return this.extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String getTypeinfo() {
		return this.typeinfo;
	}

	public void setTypeinfo(String typeinfo) {
		this.typeinfo = typeinfo;
	}

	public String toString() {
		return new ToStringBuilder(this).append("formcode", getFormcode())
				.toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TCsForm))
			return false;
		TCsForm castOther = (TCsForm) other;
		return new EqualsBuilder().append(this.getFormcode(),
				castOther.getFormcode()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getFormcode()).toHashCode();
	}

	public String getSubform() {
		return subform;
	}

	public void setSubform(String subform) {
		this.subform = subform;
	}

	public String getViewbutinfo() {
		return viewbutinfo;
	}

	public void setViewbutinfo(String viewbutinfo) {
		this.viewbutinfo = viewbutinfo;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getSqlinfo() {
		return sqlinfo;
	}

	public void setSqlinfo(String sqlinfo) {
		this.sqlinfo = sqlinfo;
	}

	public String getBelongx() {
		return belongx;
	}

	public void setBelongx(String belongx) {
		this.belongx = belongx;
	}

	public String getDimdata() {
		return dimdata;
	}

	public void setDimdata(String dimdata) {
		this.dimdata = dimdata;
	}

	public String getStyleinfo() {
		return styleinfo;
	}

	public void setStyleinfo(String styleinfo) {
		this.styleinfo = styleinfo;
	}

}
