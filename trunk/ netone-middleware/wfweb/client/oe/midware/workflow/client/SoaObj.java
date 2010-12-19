package oe.midware.workflow.client;

import java.io.Serializable;

public class SoaObj implements Serializable{

	ActiveBind activity;

	TaskObject task;
	
	ScriptObject script;

	public ScriptObject getScript() {
		return script;
	}

	public void setScript(ScriptObject script) {
		this.script = script;
	}

	public ActiveBind getActivity() {
		return activity;
	}

	public void setActivity(ActiveBind activity) {
		this.activity = activity;
	}

	public TaskObject getTask() {
		return task;
	}

	public void setTask(TaskObject task) {
		this.task = task;
	}

}
