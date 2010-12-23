package oe.bi.dao.ui.obj;

import java.io.Serializable;
import java.util.Map;

/**
 * 指标元素对象
 * 
 * @author chen.jia.xun
 * 
 */
public class TargetObj implements Serializable {

	private String tragetid;

	private String nativeid;// 保存原生ID，通过是 Sql表单中 真实表的字段，nativeid用在 向导中
	// 构造SQL子条件的时候使用
	private String tragetname;

	private String tragetJavatype;

	private String groupid;

	private Map extendattribute;

	public Map getExtendattribute() {
		return extendattribute;
	}

	public void setExtendattribute(Map extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getTragetid() {
		return tragetid;
	}

	public void setTragetid(String tragetid) {
		this.tragetid = tragetid;
	}

	public String getTragetJavatype() {
		return tragetJavatype;
	}

	public void setTragetJavatype(String tragetJavatype) {
		this.tragetJavatype = tragetJavatype;
	}

	public String getTragetname() {
		return tragetname;
	}

	public void setTragetname(String tragetname) {
		this.tragetname = tragetname;
	}

	public String getNativeid() {
		return nativeid;
	}

	public void setNativeid(String nativeid) {
		this.nativeid = nativeid;
	}

}
