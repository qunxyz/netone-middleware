package oe.bi.dao.ui.obj;

import java.io.Serializable;
import java.util.Map;

/**
 * ָ��Ԫ�ض���
 * 
 * @author chen.jia.xun
 * 
 */
public class TargetObj implements Serializable {

	private String tragetid;

	private String nativeid;// ����ԭ��ID��ͨ���� Sql���� ��ʵ����ֶΣ�nativeid���� ����
	// ����SQL��������ʱ��ʹ��
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
