package oe.midware.workflow.runtime.ormobj;

import java.io.Serializable;

import oe.frame.orm.OrmerEntry;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TWfRelevantvar implements Serializable {

	/** identifier field */
	private String varcode;

	/** nullable persistent field */
	private String runtimeid;

	/** nullable persistent field */
	private String valuenow;

	/** persistent field */
	private String datafieldid;

	/** nullable persistent field */
	private String extendattribute;

	private String processid;

	private String participant;

	private String nameExt;

	public String getNameExt() {
		return nameExt;
	}

	public void setNameExt(String nameExt) {
		this.nameExt = nameExt;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	/** full constructor */
	public TWfRelevantvar(String runtimeid, String valuenow,
			String datafieldid, String extendattribute) {
		this.runtimeid = runtimeid;
		this.valuenow = valuenow;
		this.datafieldid = datafieldid;
		this.extendattribute = extendattribute;
	}

	/** default constructor */
	public TWfRelevantvar() {
	}

	/** minimal constructor */
	public TWfRelevantvar(String datafieldid) {
		this.datafieldid = datafieldid;
	}

	public String getVarcode() {
		return this.varcode;
	}

	public void setVarcode(String varcode) {
		this.varcode = varcode;
	}

	public String getRuntimeid() {
		return this.runtimeid;
	}

	public void setRuntimeid(String runtimeid) {
		this.runtimeid = runtimeid;
	}

	public String getValuenow() {
		return this.valuenow;
	}

	public void setValuenow(String valuenow) {
		this.valuenow = valuenow;
	}

	public String getDatafieldid() {
		return this.datafieldid;
	}

	public void setDatafieldid(String datafieldid) {
		this.datafieldid = datafieldid;
	}

	public String getExtendattribute() {
		return this.extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String toString() {
		return new ToStringBuilder(this).append("varcode", getVarcode())
				.toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TWfRelevantvar))
			return false;
		TWfRelevantvar castOther = (TWfRelevantvar) other;
		return new EqualsBuilder().append(this.getVarcode(),
				castOther.getVarcode()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getVarcode()).toHashCode();
	}

	// ///////À©Õ¹·½·¨/////////////
	public TWfRuntime fetchRuntime() {
		return (TWfRuntime) OrmerEntry.fetchOrmer().fetchQuerister()
				.loadObject(TWfRuntime.class, this.runtimeid);
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

}
