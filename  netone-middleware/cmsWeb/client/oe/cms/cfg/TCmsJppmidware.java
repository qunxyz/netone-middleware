package oe.cms.cfg;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TCmsJppmidware implements Serializable {

	/** identifier field */
	private String jppmid;

	private String naturalname;

	/** nullable persistent field */
	private String jppmidname;

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

	/** full constructor */
	public TCmsJppmidware(String jppmidname, String accesstype,
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
	public TCmsJppmidware() {
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
		if (!(other instanceof TCmsJppmidware))
			return false;
		TCmsJppmidware castOther = (TCmsJppmidware) other;
		return new EqualsBuilder().append(this.getJppmid(),
				castOther.getJppmid()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getJppmid()).toHashCode();
	}

	public String getNaturalname() {
		return naturalname;
	}

	public void setNaturalname(String naturalname) {
		this.naturalname = naturalname;
	}

}
