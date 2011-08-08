package oe.midware.workflow.client;

import java.io.Serializable;
import java.util.List;

public class ActiveBind implements Serializable {

	String name;

	String bindsource;

	boolean sync;

	List<Paramobj> params;

	public String getBindsource() {
		return bindsource;
	}

	public void setBindsource(String bindsource) {
		this.bindsource = bindsource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Paramobj> getParams() {
		return params;
	}

	public void setParams(List<Paramobj> params) {
		this.params = params;
	}

	public boolean isSync() {
		return sync;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}

}
