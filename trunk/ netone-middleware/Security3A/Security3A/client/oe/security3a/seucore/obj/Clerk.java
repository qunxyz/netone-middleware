package oe.security3a.seucore.obj;

public class Clerk extends Person {

	private String deptment;

	private String extendattribute;
	
	private String ids;//唯一码用在手机端
	
	private String types;//其他类型 types=1 目标用在是否需要提醒使用 1表示不需要提醒
	
	//操作返回信息
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
