package oe.security3a.seucore.obj;

public class Clerk extends Person {

	private String deptment;

	private String extendattribute;
	
	private String ids;//Ψһ�������ֻ���
	
	private String types;//�������� types=1 Ŀ�������Ƿ���Ҫ����ʹ�� 1��ʾ����Ҫ����
	
	//����������Ϣ
	private String operationinfo;

	public String getDeptment() {
		return deptment;
	}

	public void setDeptment(String deptment) {
		this.deptment = deptment;
	}

	public String getExtendattribute() {
		return extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String getOperationinfo() {
		return operationinfo;
	}

	public void setOperationinfo(String operationinfo) {
		this.operationinfo = operationinfo;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}
	
	
	
	

}
