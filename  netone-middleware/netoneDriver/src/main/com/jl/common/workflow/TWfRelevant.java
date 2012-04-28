package com.jl.common.workflow;

public final class TWfRelevant {
	/**
	 * 表单id
	 */
	String revid;
	/**
	 * 表单名
	 */
	String revname;
	/**
	 * 流程变量绑定的表单字段ID
	 */
	String rev2column;
	/**
	 * 流程变量绑定的表单
	 */
	String rev2formcode;

	String script;

	String length;//配置待办列表显示字段长度
	
	String actname;//配置待办列表显示固定字段长度
	
	String commitername;//配置待办列表显示固定字段长度
	
	String starttime;//配置待办列表显示固定字段长度

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
