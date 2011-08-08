package oe.midware.workflow.client;

import java.io.Serializable;

public class TaskObject implements Serializable{

	String name;
	
	String activityname;
	
	String starttime;
	
	String choicemode;
	
	String loopmode;
	
	String endtime;

	public String getActivityname() {
		return activityname;
	}

	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}

	public String getChoicemode() {
		return choicemode;
	}

	public void setChoicemode(String choicemode) {
		this.choicemode = choicemode;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getLoopmode() {
		return loopmode;
	}

	public void setLoopmode(String loopmode) {
		this.loopmode = loopmode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
}
