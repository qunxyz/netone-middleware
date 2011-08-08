package oe.midware.workflow.client;

import java.io.Serializable;

public class Paramobj implements Serializable{

	String name;

	String datamode;

	String source;

	String node;
	
	String value;

	String script;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDatamode() {
		return datamode;
	}

	public void setDatamode(String datamode) {
		this.datamode = datamode;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
