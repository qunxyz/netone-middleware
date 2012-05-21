package oe.netone.app;

public class ResourData {
    public String Naturalname;
    public String name;
    public String Objecttype;
    public String appid;
    public String Parentdir;

	public String getNaturalname() {
		return Naturalname;
	}
	public void setNaturalname(String naturalname) {
		Naturalname = naturalname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getObjecttype() {
		return Objecttype;
	}
	public void setObjecttype(String objecttype) {
		Objecttype = objecttype;
	}
	public String getParentdir() {
		return Parentdir;
	}
	public void setParentdir(String parentdir) {
		Parentdir = parentdir;
	}
}
