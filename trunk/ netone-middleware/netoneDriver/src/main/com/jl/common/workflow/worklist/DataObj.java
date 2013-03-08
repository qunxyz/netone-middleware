package com.jl.common.workflow.worklist;


import com.jl.common.workflow.TWfWorklistExt;

public final class DataObj {

	String[] data;

	String[] id;

	TWfWorklistExt ext;

	String url;
	
	String starttime;

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}

	public String[] getId() {
		return id;
	}

	public void setId(String[] id) {
		this.id = id;
	}

	public TWfWorklistExt getExt() {
		return ext;
	}

	public void setExt(TWfWorklistExt ext) {
		this.ext = ext;
	}

}
