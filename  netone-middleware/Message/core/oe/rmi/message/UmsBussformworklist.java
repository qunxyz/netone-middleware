package oe.rmi.message;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UmsBussformworklist implements Serializable {

	/** identifier field */
	private String workid;

	/** nullable persistent field */
	private Date created;

	/** nullable persistent field */
	private String participant;

	private String participantname;

	/** nullable persistent field */
	private String extattr1;

	/** nullable persistent field */
	private String extattr2;

	/** nullable persistent field */
	private String extattr3;

	/** nullable persistent field */
	private String extattr4;

	/** nullable persistent field */
	private String statusinfo;

	/** nullable persistent field */
	private String sender;

	private String sendername;

	// 消息头是用来确定消息路由的,通过消息头系统可以定位到不同的消息驱动类, 并且可以发送消息到目的地
	private String password;

	public String getParticipantname() {
		return participantname;
	}

	public void setParticipantname(String participantname) {
		this.participantname = participantname;
	}

	public String getSendername() {
		return sendername;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
	}

	/** full constructor */
	public UmsBussformworklist(Date created, String participant,
			String extattr1, String extattr2, String extattr3, String extattr4,
			String statusinfo, String sender) {
		this.created = created;
		this.participant = participant;
		this.extattr1 = extattr1;
		this.extattr2 = extattr2;
		this.extattr3 = extattr3;
		this.extattr4 = extattr4;
		this.statusinfo = statusinfo;
		this.sender = sender;
	}

	/** default constructor */
	public UmsBussformworklist() {
	}

	public String getWorkid() {
		return this.workid;
	}

	public void setWorkid(String workid) {
		this.workid = workid;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getParticipant() {
		return this.participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getExtattr1() {
		return this.extattr1;
	}

	public void setExtattr1(String extattr1) {
		this.extattr1 = extattr1;
	}

	public String getExtattr2() {
		return this.extattr2;
	}

	public void setExtattr2(String extattr2) {
		this.extattr2 = extattr2;
	}

	public String getExtattr3() {
		return this.extattr3;
	}

	public void setExtattr3(String extattr3) {
		this.extattr3 = extattr3;
	}

	public String getExtattr4() {
		return this.extattr4;
	}

	public void setExtattr4(String extattr4) {
		this.extattr4 = extattr4;
	}

	public String getStatusinfo() {
		return this.statusinfo;
	}

	public void setStatusinfo(String statusinfo) {
		this.statusinfo = statusinfo;
	}

	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String toString() {
		return new ToStringBuilder(this).append("workid", getWorkid())
				.toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof UmsBussformworklist))
			return false;
		UmsBussformworklist castOther = (UmsBussformworklist) other;
		return new EqualsBuilder().append(this.getWorkid(),
				castOther.getWorkid()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getWorkid()).toHashCode();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
