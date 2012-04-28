package com.jl.common.workflow.worklist;

import java.util.List;

import com.jl.common.workflow.TWfWorklistExt;

public final class DataObj {

	String[] data;

	String[] id;

	TWfWorklistExt ext;

	String url;

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
