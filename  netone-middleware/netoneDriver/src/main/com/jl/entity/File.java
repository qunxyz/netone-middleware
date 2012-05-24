package com.jl.entity;

import java.util.Date;

public class File {
	private String unid;
	private String d_unid;
	private String u_unid;
	private String f_size;
	private String f_type;
	private String address;
	private String filename;
	private String updatetime;
	/** ¹¤×÷Á÷±àÂë */
	private String wf_code;
	private String note;

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public String getD_unid() {
		return d_unid;
	}

	public void setD_unid(String d_unid) {
		this.d_unid = d_unid;
	}

	public String getU_unid() {
		return u_unid;
	}

	public void setU_unid(String u_unid) {
		this.u_unid = u_unid;
	}

	public String getF_size() {
		return f_size;
	}

	public void setF_size(String f_size) {
		this.f_size = f_size;
	}

	public String getF_type() {
		return f_type;
	}

	public void setF_type(String f_type) {
		this.f_type = f_type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getWf_code() {
		return wf_code;
	}

	public void setWf_code(String wf_code) {
		this.wf_code = wf_code;
	}
}
