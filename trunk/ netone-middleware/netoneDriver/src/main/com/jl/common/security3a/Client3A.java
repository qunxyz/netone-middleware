package com.jl.common.security3a;

/**
 * �ͻ���Ϣ
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public final class Client3A {
	// �û���ʾ��
	String name;
	// �û���
	String clientId;
	// �û����� �м���Ľڵ�
	String belongto;
	// �û����� ҵ��ϵͳ�Ľڵ�
	String bussid;
	// �û� ��ȫKEY
	String key;

	String mobile;
	String email;
	
	String deptid;
	String deptname;

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getBelongto() {
		return belongto;
	}

	public void setBelongto(String belongto) {
		this.belongto = belongto;
	}

	public String getBussid() {
		return bussid;
	}

	public void setBussid(String bussid) {
		this.bussid = bussid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
