package oe.midware.workflow.runtime.ormobj;

import java.io.Serializable;

import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.runtime.RuntimeWorklistRef;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TWfWorklist implements Serializable {
	/** identifier field */
	private String workcode;

	/** nullable persistent field */
	private String runtimeid;

	/** nullable persistent field */
	private String starttime;

	/** nullable persistent field */
	private String donetime;

	/** nullable persistent field */
	private String executestatus;

	/** persistent field */
	private String activityid;

	/** nullable persistent field */
	private String types;

	private String processid;

	private String participant;

	private String extendattribute;

	private String nameExt;

	public String getExtendattribute() {
		return extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	/** full constructor */
	public TWfWorklist(String runtimeid, String starttime, String donetime,
			String executestatus, String activityid, String extendattribute) {
		this.runtimeid = runtimeid;
		this.starttime = starttime;
		this.donetime = donetime;
		this.executestatus = executestatus;
		this.activityid = activityid;
		this.types = extendattribute;
	}

	/** default constructor */
	public TWfWorklist() {
	}

	/** minimal constructor */
	public TWfWorklist(String activityid) {
		this.activityid = activityid;
	}

	public String getWorkcode() {
		return this.workcode;
	}

	public void setWorkcode(String workcode) {
		this.workcode = workcode;
	}

	public String getRuntimeid() {
		return this.runtimeid;
	}

	public void setRuntimeid(String runtimeid) {
		this.runtimeid = runtimeid;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getDonetime() {
		return this.donetime;
	}

	public void setDonetime(String donetime) {
		this.donetime = donetime;
	}

	public String getExecutestatus() {
		return this.executestatus;
	}

	public void setExecutestatus(String executestatus) {
		this.executestatus = executestatus;
	}

	public String getActivityid() {
		return this.activityid;
	}

	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String toString() {
		return new ToStringBuilder(this).append("workcode", getWorkcode())
				.toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TWfWorklist))
			return false;
		TWfWorklist castOther = (TWfWorklist) other;
		return new EqualsBuilder().append(this.getWorkcode(),
				castOther.getWorkcode()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getWorkcode()).toHashCode();
	}

	// /////À©Õ¹·½·¨/////////////
	public boolean isDone() {
		if (RuntimeWorklistRef.STATUS_DONE[0].equals(this.executestatus)) {
			return true;
		}
		return false;
	}

	public boolean isException() {
		if (RuntimeWorklistRef.STATUS_EXCEPTION[0].equals(this
				.getExecutestatus())) {
			return true;
		}
		return false;

	}

	public boolean isQuash() {
		if (RuntimeWorklistRef.STATUS_QUASH[0].equals(this.executestatus)) {
			return true;
		}
		return false;

	}

	public boolean isRunning() {
		if (RuntimeWorklistRef.STATUS_RUNNING[0].equals(this.executestatus)) {
			return true;
		}
		return false;

	}

	public boolean isSubflowWaiting() {
		if (RuntimeWorklistRef.STATUS_SUBFLOW_WAITING[0]
				.equals(this.executestatus)) {
			return true;
		}
		return false;
	}

	public boolean isStop() {
		if (RuntimeWorklistRef.STATUS_SUBFLOW_STOP[0]
				.equals(this.executestatus)) {
			return true;
		}
		return false;
	}

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

	public String getNameExt() {
		return nameExt;
	}

	public void setNameExt(String nameExt) {
		this.nameExt = nameExt;
	}
	
	public String getStatusExt() {
		if (this.executestatus == null || this.executestatus.trim().equals("")) {
			return "";
		}
		try {
			int index = Integer.parseInt(this.executestatus) - 1;
			return RuntimeWorklistRef.STATUSLIST[index][1];
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	


}
