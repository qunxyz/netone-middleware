package oe.common.security3a;

/**
 * �ͻ���Ϣ
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class Client3A {
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

}
