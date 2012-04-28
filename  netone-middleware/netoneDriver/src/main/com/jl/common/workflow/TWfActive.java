package com.jl.common.workflow;

import java.util.Map;

import oe.midware.workflow.xpdl.model.activity.Activity;

/**
 * 工作流活动节点信息
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class TWfActive {

	String name;

	String id;

	String processid;

	String processname;
	/**
	 * 参与者
	 */
	String participant;
	/**
	 * 参与者模式
	 */
	String participantmode;
	/**
	 * 是否单人审批
	 */
	boolean singleman;
	/**
	 * 多人审批是否需要同步
	 */
	boolean needsync;
	/**
	 * 人员是否自动指派
	 */
	boolean autoroute;
	/**
	 * 是否抄送
	 */
	boolean needAssistant;
	/**
	 * 是否抄阅
	 */
	boolean needReader;

	boolean needTree;
	/**
	 * 是否需要编辑表单
	 */
	boolean formEdit;
	/**
	 * 如果需要编辑表单，那么具体可编辑的字段列表
	 */
	String editcolumn;

	boolean fobitzb;

	boolean needsearch;

	Map subformmode;
	boolean syncto;//分布式提交判断



	public boolean isFobitzb() {
		return fobitzb;
	}

	public void setFobitzb(boolean fobitzb) {
		this.fobitzb = fobitzb;
	}

	public boolean isFormEdit() {
		return formEdit;
	}

	public void setFormEdit(boolean formEdit) {
		this.formEdit = formEdit;
	}

	public boolean isNeedTree() {
		return needTree;
	}

	public void setNeedTree(boolean needTree) {
		this.needTree = needTree;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getParticipantmode() {
		return participantmode;
	}

	public void setParticipantmode(String participantmode) {
		this.participantmode = participantmode;
	}

	public boolean isSingleman() {
		return singleman;
	}

	public void setSingleman(boolean singleman) {
		this.singleman = singleman;
	}

	public boolean isNeedsync() {
		return needsync;
	}

	public void setNeedsync(boolean needsync) {
		this.needsync = needsync;
	}

	public boolean isAutoroute() {
		return autoroute;
	}

	public void setAutoroute(boolean autoroute) {
		this.autoroute = autoroute;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProcessname() {
		return processname;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public boolean isNeedAssistant() {
		return needAssistant;
	}

	public void setNeedAssistant(boolean needAssistant) {
		this.needAssistant = needAssistant;
	}

	public boolean isNeedReader() {
		return needReader;
	}

	public void setNeedReader(boolean needReader) {
		this.needReader = needReader;
	}

	public String getEditcolumn() {
		return editcolumn;
	}

	public void setEditcolumn(String editcolumn) {
		this.editcolumn = editcolumn;
	}

	public boolean isNeedsearch() {
		return needsearch;
	}

	public void setNeedsearch(boolean needsearch) {
		this.needsearch = needsearch;
	}

	public Map getSubformmode() {
		return subformmode;
	}

	public void setSubformmode(Map subformmode) {
		this.subformmode = subformmode;
	}

	public boolean isSyncto() {
		return syncto;
	}

	public void setSyncto(boolean syncto) {
		this.syncto = syncto;
	}

}
