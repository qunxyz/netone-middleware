package com.jl.common.workflow;

import java.util.Map;

import oe.midware.workflow.xpdl.model.activity.Activity;

/**
 * ��������ڵ���Ϣ
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
	 * ������
	 */
	String participant;
	/**
	 * ������ģʽ
	 */
	String participantmode;
	/**
	 * �Ƿ�������
	 */
	boolean singleman;
	/**
	 * ���������Ƿ���Ҫͬ��
	 */
	boolean needsync;
	/**
	 * ��Ա�Ƿ��Զ�ָ��
	 */
	boolean autoroute;
	/**
	 * �Ƿ���
	 */
	boolean needAssistant;
	/**
	 * �Ƿ���
	 */
	boolean needReader;

	boolean needTree;
	/**
	 * �Ƿ���Ҫ�༭��
	 */
	boolean formEdit;
	/**
	 * �����Ҫ�༭������ô����ɱ༭���ֶ��б�
	 */
	String editcolumn;

	boolean fobitzb;

	boolean needsearch;

	Map subformmode;
	boolean syncto;//�ֲ�ʽ�ύ�ж�



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
