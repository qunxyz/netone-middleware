package com.jl.common.workflow;

public final class TWfRelevant {
	/**
	 * ��id
	 */
	String revid;
	/**
	 * ����
	 */
	String revname;
	/**
	 * ���̱����󶨵ı��ֶ�ID
	 */
	String rev2column;
	/**
	 * ���̱����󶨵ı�
	 */
	String rev2formcode;

	String script;

	String length;//���ô����б���ʾ�ֶγ���
	
	String actname;//���ô����б���ʾ�̶��ֶγ���
	
	String commitername;//���ô����б���ʾ�̶��ֶγ���
	
	String starttime;//���ô����б���ʾ�̶��ֶγ���

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getRevid() {
		return revid;
	}

	public void setRevid(String revid) {
		this.revid = revid;
	}

	public String getRevname() {
		return revname;
	}

	public void setRevname(String revname) {
		this.revname = revname;
	}

	public String getRev2column() {
		return rev2column;
	}

	public void setRev2column(String rev2column) {
		this.rev2column = rev2column;
	}

	public String getRev2formcode() {
		return rev2formcode;
	}

	public void setRev2formcode(String rev2formcode) {
		this.rev2formcode = rev2formcode;
	}

	public String getActname() {
		return actname;
	}

	public void setActname(String actname) {
		this.actname = actname;
	}

	public String getCommitername() {
		return commitername;
	}

	public void setCommitername(String commitername) {
		this.commitername = commitername;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

}
