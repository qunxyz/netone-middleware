package oe.midware.workflow.client;

import java.io.Serializable;

public class ScriptObject implements Serializable {

	String name;

	String activityname;

	String cdata;

	public String getActivityname() {
		return activityname;
	}

	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCdata() {
		return cdata;
	}

	public void setCdata(String cdata) {
		this.cdata = cdata;
	}

}
