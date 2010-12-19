package oe.cms.web.infocellSimple;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class InfoCellModelDIYForm extends ActionForm {

	/** identifier field */
	private String jppmid;

	/** nullable persistent field */
	private String jppmidname;

	private String naturalname;

	/** nullable persistent field */
	private String accesstype;

	/** nullable persistent field */
	private String jppmidscript;

	/** nullable persistent field */
	private String jppmidscriptapi;

	/** nullable persistent field */
	private String participant;

	/** nullable persistent field */
	private String extendattribute;

	// ////////
	/** identifier field */
	private String editindex;

	/** full constructor */
	public InfoCellModelDIYForm(String jppmidname, String accesstype,
			String jppmidscript, String jppmidscriptapi, String participant,
			String extendattribute) {
		this.jppmidname = jppmidname;
		this.accesstype = accesstype;
		this.jppmidscript = jppmidscript;
		this.jppmidscriptapi = jppmidscriptapi;
		this.participant = participant;
		this.extendattribute = extendattribute;
	}

	/** default constructor */
	public InfoCellModelDIYForm() {
	}

	public String getJppmid() {
		return this.jppmid;
	}

	public void setJppmid(String jppmid) {
		this.jppmid = jppmid;
	}

	public String getJppmidname() {
		return this.jppmidname;
	}

	public void setJppmidname(String jppmidname) {
		this.jppmidname = jppmidname;
	}

	public String getAccesstype() {
		return this.accesstype;
	}

	public void setAccesstype(String accesstype) {
		this.accesstype = accesstype;
	}

	public String getJppmidscript() {
		return this.jppmidscript;
	}

	public void setJppmidscript(String jppmidscript) {
		this.jppmidscript = jppmidscript;
	}

	public String getJppmidscriptapi() {
		return this.jppmidscriptapi;
	}

	public void setJppmidscriptapi(String jppmidscriptapi) {
		this.jppmidscriptapi = jppmidscriptapi;
	}

	public String getParticipant() {
		return this.participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getExtendattribute() {
		return this.extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String toString() {
		return new ToStringBuilder(this).append("jppmid", getJppmid())
				.toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof InfoCellModelDIYForm))
			return false;
		InfoCellModelDIYForm castOther = (InfoCellModelDIYForm) other;
		return new EqualsBuilder().append(this.getJppmid(),
				castOther.getJppmid()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getJppmid()).toHashCode();
	}

	public String getEditindex() {
		return editindex;
	}

	public void setEditindex(String editindex) {
		this.editindex = editindex;
	}

	public String getNaturalname() {
		return naturalname;
	}

	public void setNaturalname(String naturalname) {
		this.naturalname = naturalname;
	}

}
