package oe.common.security3a;

/**
 * 客户信息
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class Client3A {
	// 用户显示名
	String name;
	// 用户名
	String clientId;
	// 用户隶属 中间件的节点
	String belongto;
	// 用户隶属 业务系统的节点
	String bussid;
	// 用户 安全KEY
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
