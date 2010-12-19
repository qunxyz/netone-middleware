package oe.midware.workflow.runtime.ormobj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.bus.workflow.WfEntry;
import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.runtime.RuntimeProcessRef;
import oe.midware.workflow.runtime.RuntimeWorklistRef;
import oe.midware.workflow.runtime.XpdlModelHandler;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TWfRuntime implements Serializable {
	/** identifier field */
	private String runtimeid;

	/** persistent field */
	private String processid;

	/** nullable persistent field */
	private String starttime;

	/** nullable persistent field */
	private String endtime;

	/** nullable persistent field */
	private String statusnow;

	/** nullable persistent field */
	private String kind;

	/** nullable persistent field */
	private String belongruntimeid;

	/** nullable persistent field */
	private String belongactivityid;

	/** nullable persistent field */
	private String priority;

	/** nullable persistent field */
	private String limits;

	/** nullable persistent field */
	private String extendattribute;

	private String participant;

	private String nameExt;

	/** full constructor */
	public TWfRuntime(String processid, String starttime, String endtime,
			String statusnow, String kind, String belongruntimeid,
			String belongactivityid, String priority, String limits,
			String extendattribute) {
		this.processid = processid;
		this.starttime = starttime;
		this.endtime = endtime;
		this.statusnow = statusnow;
		this.kind = kind;
		this.belongruntimeid = belongruntimeid;
		this.belongactivityid = belongactivityid;
		this.priority = priority;
		this.limits = limits;
		this.extendattribute = extendattribute;
	}

	/** default constructor */
	public TWfRuntime() {
	}

	/** minimal constructor */
	public TWfRuntime(String processid) {
		this.processid = processid;
	}

	public String getRuntimeid() {
		return this.runtimeid;
	}

	public void setRuntimeid(String runtimeid) {
		this.runtimeid = runtimeid;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getStatusnow() {
		return this.statusnow;
	}

	public void setStatusnow(String statusnow) {
		this.statusnow = statusnow;
	}

	public String getKind() {
		return this.kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getBelongruntimeid() {
		return this.belongruntimeid;
	}

	public void setBelongruntimeid(String belongruntimeid) {
		this.belongruntimeid = belongruntimeid;
	}

	public String getBelongactivityid() {
		return this.belongactivityid;
	}

	public void setBelongactivityid(String belongactivityid) {
		this.belongactivityid = belongactivityid;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getLimits() {
		return this.limits;
	}

	public void setLimits(String limits) {
		this.limits = limits;
	}

	public String getExtendattribute() {
		return this.extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String toString() {
		return new ToStringBuilder(this).append("runtimeid", getRuntimeid())
				.toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TWfRuntime))
			return false;
		TWfRuntime castOther = (TWfRuntime) other;
		return new EqualsBuilder().append(this.getRuntimeid(),
				castOther.getRuntimeid()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getRuntimeid()).toHashCode();
	}

	// //////////////////////////////////////////////
	// /////////////////À©Õ¹·½·¨//////////////////////
	// /////////////////////////////////////////////

	public boolean isReady() {

		if (RuntimeProcessRef.STATUS_READY[0].equals(this.getStatusnow())) {
			return true;
		}
		return false;
	}

	public boolean isRunning() {
		if (RuntimeProcessRef.STATUS_RUNNING[0].equals(this.getStatusnow())) {
			return true;
		}
		return false;
	}

	public boolean isException() {
		if (RuntimeProcessRef.STATUS_EXCEPTION[0].equals(this.getStatusnow())) {
			return true;
		}
		return false;
	}

	public boolean isQuash() {
		if (RuntimeProcessRef.STATUS_QUASH[0].equals(this.getStatusnow())) {
			return true;
		}
		return false;
	}

	public boolean isDone() {
		if (RuntimeProcessRef.STATUS_END[0].equals(this.getStatusnow())) {
			return true;
		}
		return false;
	}

	public String getTypeExt() {
		if (this.getKind().equals(RuntimeProcessRef.TYPE_MAIN_FLOW[0])) {
			return RuntimeProcessRef.TYPE_MAIN_FLOW[1];
		} else if (this.getKind().equals(RuntimeProcessRef.TYPE_SUB_FLOW[0])) {
			return RuntimeProcessRef.TYPE_SUB_FLOW[1];
		} else if (this.getKind().equals(
				RuntimeProcessRef.TYPE_SYNC_SUB_FLOW[0])) {
			return RuntimeProcessRef.TYPE_SYNC_SUB_FLOW[1];
		}
		return null;
	}

	public String getStatusExt() {
		if (this.getStatusnow() == null
				|| this.getStatusnow().trim().equals("")) {
			return null;
		}
		int index = new Integer(this.getStatusnow()).intValue();
		return RuntimeProcessRef.STATUSLIST[index][1];
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public boolean isMainFlow() {
		if (RuntimeProcessRef.TYPE_MAIN_FLOW[0].equals(this.getKind())) {
			return true;
		}
		return false;
	}

	public boolean isSubFlow() {
		if (RuntimeProcessRef.TYPE_SUB_FLOW[0].equals(this.getKind())) {
			return true;
		}
		return false;
	}

	public boolean isSyncSubFlow() {
		if (RuntimeProcessRef.TYPE_SYNC_SUB_FLOW[0].equals(this.getKind())) {
			return true;
		}
		return false;
	}

	public String getNameExt() {
		return nameExt;
	}

	public void setNameExt(String nameExt) {
		this.nameExt = nameExt;
	}
}
