package oe.mid.datatools.obj;

public class DColumnref {

	String name;

	String refertoname;
	
	String script;

	String type;// type为空时使用参考字段的类型，
	
	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRefertoname() {
		return refertoname;
	}

	public void setRefertoname(String refertoname) {
		this.refertoname = refertoname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
